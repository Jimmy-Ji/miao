package com.miao.controller.base

import com.miao.common.base.BaseCriterion
import com.miao.common.page.PageInfo

/**
  * Created by pp on 2017/5/12.
  */
class BaseController extends BaseCriterion{
  /**
    * 保存实体类
    *
    * @param v 传入实体类对象
    * @return
    */
  override def save[V](v: V): V = ???

  /**
    * 批量保存
    *
    * @param list
    * @return
    */
override def saveByList[V](list: List[V]): List[V] = ???

  /**
    * 根据id删除某条数据
    *
    * @param k
    * @return 返回删除条数
    */
  override def delById[K, V](k: K, c: Class[V]): Int = ???

  /**
    * 根据多个id删除多条数据
    *
    * @param list ids
    * @return del success count
    */
  override def delByListIds[K, V](list: List[K], c: Class[V]): Int = ???

  /**
    * 清空表
    *
    * @param c
    * @return
    */
  override def delAll[V](c: Class[V]): Int = ???

  /**
    * 修改某条记录
    *
    * @param v
    * @return
    */
  override def update[V](v: V): V = ???

  /**
    * 批量修改
    *
    * @param list
    * @return
    */
  override def updateByList[V](list: List[V]): List[V] = ???

  /**
    * 根据id查询
    *
    * @param k
    */
  override def select[K, V](k: K, c: Class[V]): V = ???

  /**
    * 根据id批量查询
    *
    * @param list
    */
  override def selectByList[K, V](list: List[K], c: Class[V]): List[V] = ???

  /**
    * 分页查询
    *
    * @param pageInfo
    * @return
    */
  override def queryByPage[V](pageInfo: PageInfo[V], c: Class[V]): PageInfo[V] = ???
}
