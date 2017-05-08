package com.miao.controller

import com.miao.model.User
import org.springframework.web.bind.annotation._

/**
  * Created by pp on 2017/5/8.
  */
@RestController
class UserController extends BaseController[String,User]{

  @RequestMapping(value=Array("thello.do"),method = Array(RequestMethod.GET))
  def test() : String={
    "hello world"
  }

  @RequestMapping(value=Array("test.do"),method = Array(RequestMethod.POST),produces=Array("application/json"))
  def add(@RequestBody user: User):String = {
    println(" controller === "+user.getName)
    this.save(user)
    user.getName
  }

}
