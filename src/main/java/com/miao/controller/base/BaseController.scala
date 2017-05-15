package com.miao.controller.base

import javax.annotation.Resource

import com.miao.common.base.BaseCriterion
import com.miao.common.page.PageInfo
import com.miao.service.base.BaseService
import org.springframework.stereotype.Controller

import scala.beans.BeanProperty

/**
  * Created by pp on 2017/5/12.
  */
@Controller
class BaseController extends BaseCriterion{

  @Resource
  var baseService : BaseService = _

  /**
    * 保存实体类
    *
    * @param v 传入实体类对象
    * @return
    */
  override def save[V](v: V): V = {
    baseService.save(v)
  }

  /**
    * 批量保存
    *
    * @param list
    * @return
    */
  override def saveByList[V](list: List[V]): List[V] = {
    baseService.saveByList(list)
  }

  /**
    * 根据id删除某条数据
    *
    * @param k
    * @return 返回删除条数
    */
  override def delById[K, V](k: K, c: Class[V]): Int = {
    baseService.delById(k,c)
  }

  /**
    * 根据多个id删除多条数据
    *
    * @param list ids
    * @return del success count
    */
  override def delByListIds[K, V](list: List[K], c: Class[V]): Int = {
    baseService.delByListIds(list,c)
  }

  /**
    * 清空表
    *
    * @param c
    * @return
    */
  override def delAll[V](c: Class[V]): Int = {
    baseService.delAll(c)
  }

  /**
    * 修改某条记录
    *
    * @param v
    * @return
    */
  override def update[V](v: V): V = {
    baseService.update(v)
  }

  /**
    * 批量修改
    *
    * @param list
    * @return
    */
  override def updateByList[V](list: List[V]): List[V] = {
    baseService.updateByList(list)
  }

  /**
    * 根据id查询
    *
    * @param k
    */
  override def select[K, V](k: K, c: Class[V]): V = {
    baseService.select(k,c)
  }

  /**
    * 根据id批量查询
    *
    * @param list
    */
  override def selectByList[K, V](list: List[K], c: Class[V]): List[V] = {
    baseService.selectByList(list,c)
  }

  /**
    * 分页查询
    *
    * @param pageInfo
    * @return
    */
  override def queryByPage[V](pageInfo: PageInfo[V], c: Class[V]): PageInfo[V] = {
    baseService.queryByPage(pageInfo,c)
  }

}
