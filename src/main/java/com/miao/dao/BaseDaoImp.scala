package com.miao.dao

import com.miao.common.annotation.TableFiled
import com.miao.common.base.BaseDao
import com.miao.common.page.PageInfo
import com.miao.common.utils.MyUUID
import com.miao.model.User
import org.apache.commons.lang3.StringUtils
import org.apache.logging.log4j.{LogManager, Logger}
import org.springframework.jdbc.core.JdbcTemplate

/**
  * Created by root on 17-5-9.
  */
class BaseDaoImp[K,V] extends BaseDao[K,V]{

  val log : Logger = LogManager.getLogger

  private var jdbcTemplate : JdbcTemplate = _

  private val DELETE : String = "delete from table_name "
  private val INSERT : String = "insert into table_name ( "
  private val UPDATE : String = "update table_name set "

  private val uuid : MyUUID = new MyUUID(0,0)

  /**
    * 保存实体类
    *
    * @param v 传入实体类对象
    * @return
    */
  override def save(v: V): V = {
    var sql = ""
    var fileds = ""
    var values = " values ( "
    //遍历所有方法
    v.getClass.getDeclaredMethods.foreach(f => {
      log.info("==========="+f.getName)
      if (f.getName.contains("get")
        && f.getAnnotation(classOf[TableFiled]) == null
        ){
          val name = f.getName.substring(3,f.getName.length).toUpperCase
          log.info("name==="+name)
          val value = f.invoke(v)
          log.info("value === "+value)
          if (name == "ID"){
            val id = uuid.nextId()
            fileds += name +","
            values += id+","
            v.getClass.getMethod("setId",classOf[Long]).invoke(v,id.asInstanceOf[AnyRef])
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
    v.getClass
    sql = INSERT + fileds.substring(0,fileds.length-1) +") " + values.substring(0,values.length-1) + ")"
    log.info("======"+sql+"========")
    v
  }
  /**
    * 批量保存
    *
    * @param list
    * @return
    */
override def saveByList(list: List[V]): List[V] = ???

  /**
    * 根据id删除某条数据
    *
    * @param k
    * @return 返回删除条数
    */
  override def delById(k: K): Int = ???

  /**
    * 根据多个id删除多条数据
    *
    * @param list ids
    * @return del success count
    */
  override def delByListIds(list: List[K]): Int = ???

  /**
    * 修改某条记录
    *
    * @param v
    * @return
    */
  override def update(v: V): V = ???

  /**
    * 批量修改
    *
    * @param list
    * @return
    */
  override def updateByList(list: List[V]): List[V] = ???

  /**
    * 根据id查询
    *
    * @param k
    */
  override def select(k: K): Unit = ???

  /**
    * 根据id批量查询
    *
    * @param list
    */
  override def selectByList(list: List[K]): Unit = ???

  /**
    * 分页查询
    * @param pageInfo
    * @return
    */
  override def queryByPage(pageInfo: PageInfo[V]): PageInfo[V] = ???
}

object BaseDaoImp{

  def main(args: Array[String]): Unit = {
    val dao = new BaseDaoImp[String,User]
    val user = new User
    user.setAge(20 toByte)
    val us : User =dao.save(user)
    println(us.getId)
  }

}