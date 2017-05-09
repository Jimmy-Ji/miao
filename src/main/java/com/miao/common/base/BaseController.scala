package com.miao.common.base

import javax.annotation.Resource

import org.springframework.stereotype.Controller

/**
  * Created by pp on 2017/5/8.
  */
@Controller
trait BaseController[K,V] {

  @Resource
  var baseService : BaseService[K,V] = _

  def save(v: V): V ={
    baseService.save(v)
    v
  }

}
