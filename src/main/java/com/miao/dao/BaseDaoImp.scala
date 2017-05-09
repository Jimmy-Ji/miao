package com.miao.dao

import com.miao.common.base.BaseDao
import com.miao.common.page.PageInfo
import com.miao.common.utils.MyUUID
import org.springframework.jdbc.core.JdbcTemplate

/**
  * Created by root on 17-5-9.
  */
class BaseDaoImp[K,V] extends BaseDao[K,V]{

  private var jdbcTemplate : JdbcTemplate = _

  private val DELETE : String = "delete from table_name "
  private val INSERT : String = "insert into table_name "
  private val UPDATE : String = "update table_name set "

  private val uuid : MyUUID = new MyUUID(0,0)

  /**
    * 保存实体类
    *
    * @param v 传入实体类对象
    * @return
    */
  override def save(v: V): V = ???
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
