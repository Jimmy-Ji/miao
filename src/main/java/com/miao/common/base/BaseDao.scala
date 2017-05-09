package com.miao.common.base

import com.miao.common.page.PageInfo
import org.springframework.stereotype.Repository

import scala.reflect.ClassTag

/**
  * Created by pp on 2017/5/8.
  */
@Repository
trait BaseDao[K,V]{


  /**
    * 保存实体类
    * @param v 传入实体类对象
    * @return
    */
  def save(v: V) : V

  /**
    * 批量保存
    * @param list
    * @return
    */
  def saveByList(list: List[V]) : List[V]

  /**
    * 根据id删除某条数据
    * @param k
    * @return 返回删除条数
    */
  def delById(k: K) : Int

  /**
    * 根据多个id删除多条数据
    * @param list ids
    * @return del success count
    */
  def delByListIds(list: List[K]) : Int

  /**
    * 修改某条记录
    * @param v
    * @return
    */
  def update(v: V) : V

  /**
    * 批量修改
    * @param list
    * @return
    */
  def updateByList(list: List[V]) : List[V]

  /**
    * 根据id查询
    * @param k
    */
  def select(k: K)

  /**
    * 根据id批量查询
    * @param list
    */
  def selectByList(list: List[K])

  /**
    * 分页查询
    * @param pageInfo
    * @return
    */
  def queryByPage(pageInfo: PageInfo[V]) : PageInfo[V]

}
