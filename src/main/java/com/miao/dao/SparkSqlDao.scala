package com.miao.dao

import java.sql.{Connection, DriverManager}

import com.miao.common.utils.ConfigCache

/**
  * Created by pp on 2017/5/15.
  */
object SparkSqlDao {

  val url = ConfigCache.getProString("hive_url")
  val driver = ConfigCache.getProString("hive_driver")
  val user = ConfigCache.getProString("hive_user")
  val passwd = ConfigCache.getProString("hive_password")
  Class.forName(driver)

  def getConnection(): Connection ={
    DriverManager.getConnection(url,user,passwd)
  }

  def main(args: Array[String]): Unit = {
    val stm = getConnection().createStatement()
    val res = stm.executeQuery("select * from client_jump limit 1")
    while (res.next()){
      println(res.getString(1))
    }
  }
}
