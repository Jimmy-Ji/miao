package com.miao.controller

import javax.annotation.Resource
import com.miao.service.BaseService
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
  }

}
