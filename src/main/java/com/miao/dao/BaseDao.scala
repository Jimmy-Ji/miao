package com.miao.dao

import javax.annotation.Resource

import com.miao.common.annotation.TableFiled
import com.miao.common.base.BaseCriterion
import com.miao.common.page.PageInfo
import com.miao.common.utils.MyUUID
import org.apache.logging.log4j.{LogManager, Logger}
import org.springframework.jdbc.core.{BeanPropertyRowMapper, JdbcTemplate}
import org.springframework.stereotype.Repository
import scala.collection.JavaConverters._

/**
  * Created by root on 17-5-9.
  */
@Repository
class BaseDao extends BaseCriterion{

  val log : Logger = LogManager.getLogger

  @Resource
  private var jdbcTemplate : JdbcTemplate = _

  private val DELETE : String = "delete from  "
  private val INSERT : String = "insert into  ( "
  private val UPDATE : String = "update  "

  private val uuid : MyUUID = new MyUUID(0,0)

  /**
    * 保存实体类
    *
    * @param v 传入实体类对象
    * @return
    */
  override def save[V](v: V): V = {
    jdbcTemplate.update(convertInsertSQL(List(v))(0))
    v
  }
  /**
    * 批量保存
    *
    * @param list
    * @return
    */
  override def saveByList[V](list: List[V]): List[V] = {
    //使用这种方式，是将数组中的每个参数传入，而不是传入单一的数组
    jdbcTemplate.batchUpdate(convertInsertSQL(list):_*)
    list
  }

  /**
    * 根据id删除某条数据
    *
    * @param k
    * @return 返回删除条数
    */
  override def delById[K,V](k: K,c : Class[V]): Int = {
    val sql = DELETE + getTableName(c) + " where id = '"+k+"'"
    log.debug(sql)
    jdbcTemplate.update(sql)
  }

  /**
    * 根据多个id删除多条数据
    *
    * @param list ids
    * @return del success count
    */
  override def delByListIds[K,V](list: List[K],c : Class[V]): Int = {
    val params = list.map(f => f toString).reduce((a,b) => "'"+a+"','"+b+"'")
    val sql = DELETE + getTableName(c) + " WHERE ID IN ( "+params+" )"
    jdbcTemplate.update(sql)
  }

  /**
    * 清空表
    *
    * @param c
    * @return
    */
  override def delAll[V](c: Class[V]): Int = {
    val sql = DELETE + getTableName(c)
    jdbcTemplate.update(sql)
  }

  /**
    * 修改某条记录
    *
    * @param v
    * @return
    */
  override def update[V](v: V): V = {
    jdbcTemplate.update(convertUpdateSQL(List(v))(0))
    v
  }

  /**
    * 批量修改
    *
    * @param list
    * @return
    */
  override def updateByList[V](list: List[V]): List[V] = {
    jdbcTemplate.batchUpdate(convertUpdateSQL(list):_*)
    list
  }

  /**
    * 根据id查询
    *
    * @param k
    */
  override def select[K,V](k: K,c : Class[V]) : V = {
    val filed = getFields(c)
    val sql = "select "+filed+" from " + getTableName(c) + " where id = '" + k +"'"
    val rowMapper : BeanPropertyRowMapper[V] = BeanPropertyRowMapper.newInstance(c)
    jdbcTemplate.queryForObject(sql,rowMapper)
  }

  /**
    * 根据id批量查询
    *
    * @param list
    */
  override def selectByList[K,V](list: List[K],c : Class[V]) : List[V] = {
    val filed = getFields(c)
    val params = list.map(f => f toString).reduce((a,b) => "'"+a+","+b+"'")
    val sql = "select "+filed+" from " + getTableName(c) + " where id in (" + params +")"
    val rowMapper : BeanPropertyRowMapper[V] = BeanPropertyRowMapper.newInstance(c)
    val result  = jdbcTemplate.query(sql,rowMapper)
    log.debug(sql)
    asScalaBuffer(result).toList
  }

  /**
    * 分页查询
    * @param pageInfo
    * @return
    */
  override def queryByPage[V](pageInfo: PageInfo[V],c:Class[V]): PageInfo[V] = {
    val fileds = getFields(c)
    val sql = "select " + fileds + " from "+ getTableName(c) + " order by id limit " + pageInfo.getStart +","+pageInfo.getEnd
    val rowMapper : BeanPropertyRowMapper[V] = BeanPropertyRowMapper.newInstance(c)
    val result  = jdbcTemplate.query(sql,rowMapper)
    log.debug(sql)
    pageInfo.setList(asScalaBuffer(result).toList)
    pageInfo
  }

  private def convertInsertSQL[V](list: List[V]): List[String] ={
    val listSql = list.map(v => {
      var sql = ""
      var fileds = " ( "
      var values = " values ( "
      //遍历所有方法
      v.getClass.getDeclaredMethods.foreach(f => {
        if (f.getName.contains("get")
          && f.getAnnotation(classOf[TableFiled]) == null
        ){
          //超厉害的正则表达式，看不懂，功能是按大写字母切割字符串
          val name = f.getName.substring(3,f.getName.length).split("(?<!^)(?=[A-Z])").reduce((a,b)=>a+"_"+b).toLowerCase
          val value = f.invoke(v)
          if (name == "Id"){
            val id = uuid.nextId().toString
            fileds += name +","
            values += "'"+id+"',"
            //scala的Long类型是AnfVal对应着基本类型，如果java的参数是Object类型的话，需要转成java的long对象
            v.getClass.getMethod("setId",classOf[java.lang.String]).invoke(v,id)
          }
          if (value != null){
            if (f.getReturnType.getName == "java.lang.String" ){
              fileds += name +","
              values += "'"+value+"',"
            }else{
              fileds += name +","
              values += value + ","
            }
          }
        }
      })
      sql = INSERT + getTableName(v.getClass) + fileds.substring(0,fileds.length-1) +") " + values.substring(0,values.length-1) + ")"
      log.debug("======"+sql+"========")
      sql
    })
    listSql
  }

  private def convertUpdateSQL[V](list: List[V]): List[String] ={
    val listSql = list.map(v => {
      var sql = UPDATE + getTableName(v.getClass) + " set "
      var id = ""
      //遍历所有方法
      v.getClass.getDeclaredMethods.foreach(f => {
        if (f.getName.contains("get")
          && f.getAnnotation(classOf[TableFiled]) == null
        ) {
          //超厉害的正则表达式，看不懂，功能是按大写字母切割字符串
          val name = f.getName.substring(3, f.getName.length).split("(?<!^)(?=[A-Z])").reduce((a, b) => a + "_" + b).toLowerCase
          val value = f.invoke(v)
          if (name == "Id"){
            id = value.toString
          }else{
            if (value != null) {
              if (f.getReturnType.getName == "java.lang.String") {
                sql += name + "=" + "'" + value + "',"
              } else {
                sql += name + "=" + value + ","
              }
            }
          }
        }
      })
      sql = sql.substring(0, sql.length - 1)
      sql = sql + " where id = '" + id +"'"
      log.debug("======" + sql + "========")
      sql
    })
    listSql
  }

  def getFields[V](c:Class[V]): String ={
    c.getDeclaredFields.map(_.getName.split("(?<!^)(?=[A-Z])").reduce((a, b) => a + "_" + b).toLowerCase).reduce((a,b)=>a+","+b).toLowerCase
  }

  def getTableName[V](c: Class[V]) : String ={
    c.getSimpleName.split("(?<!^)(?=[A-Z])").reduce((a, b) => a + "_" + b).toLowerCase
  }

}