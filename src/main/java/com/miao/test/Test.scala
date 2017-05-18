package com.miao.test

import java.lang.reflect.Field

import com.miao.common.annotation.TableFiled
import com.miao.common.utils.ConfigCache
import com.miao.model.User

/**
  * Created by pp on 2017/5/9.
  */
class Test[V] {

  def test(v: V): Unit ={
    val f = v.getClass.getDeclaredMethods
    f.foreach(a=>println(a.getReturnType.getName))
    val id = 10000L
    v.getClass.getMethod("setId",classOf[Long]).invoke(v,id.asInstanceOf[AnyRef])
    val t = v.getClass.newInstance()
  }


}

object Test{

  def main(args: Array[String]): Unit = {

   println(ConfigCache.getProString("url")+"=========")

  }
}