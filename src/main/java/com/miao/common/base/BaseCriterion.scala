package com.miao.common.base

import com.miao.common.page.PageInfo

/**
  * Created by pp on 2017/5/12.
  */
trait BaseCriterion {
  /**
    * 保存实体类
    * @param v 传入实体类对象
    * @return
    */
  def save[V](v: V) : V

  /**
    * 批量保存
    * @param list
    * @return
    */
  def saveByList[V](list: List[V]) : List[V]

  /**
    * 根据id删除某条数据
    * @param k
    * @return 返回删除条数
    */
  def delById[K,V](k: K,c : Class[V]) : Int

  /**
    * 根据多个id删除多条数据
    * @param list ids
    * @return del success count
    */
  def delByListIds[K,V](list: List[K],c : Class[V]) : Int

  /**
    * 清空表
    * @param c
    * @return
    */
  def delAll[V](c : Class[V]): Int

  /**
    * 修改某条记录
    * @param v
    * @return
    */
  def update[V](v: V) : V

  /**
    * 批量修改
    * @param list
    * @return
    */
  def updateByList[V](list: List[V]) : List[V]

  /**
    * 根据id查询
    * @param k
    */
  def select[K,V](k: K,c : Class[V]) : V

  /**
    * 根据id批量查询
    * @param list
    */
  def selectByList[K,V](list: List[K],c : Class[V]) : List[V]

  /**
    * 分页查询
    * @param pageInfo
    * @return
    */
  def queryByPage[V](pageInfo: PageInfo[V],c:Class[V]) : PageInfo[V]
}
