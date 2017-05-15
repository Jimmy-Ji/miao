package com.miao.controller

import com.miao.common.utils.ConfigCache
import com.miao.controller.base.BaseController
import com.miao.model.{TblTest, User}
import org.springframework.web.bind.annotation._

/**
  * Created by pp on 2017/5/8.
  */
@RestController
class UserController extends BaseController{

  @RequestMapping(value=Array("thello.do"),method = Array(RequestMethod.GET))
  def test() : String={
    "hello world"
  }

  @RequestMapping(value=Array("test.do"),method = Array(RequestMethod.POST),produces=Array("application/json"))
  def add(@RequestBody user: TblTest):String = {
    println(" controller === "+user.getName+user.getAge)
    val users = save[TblTest](user)
    println(users.getId)
    println("============"+ConfigCache.getProString("jdbc_url"))
    user.toString
  }

}
