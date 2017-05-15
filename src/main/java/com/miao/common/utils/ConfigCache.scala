package com.miao.common.utils

import java.io.FileInputStream
import java.util.Properties
import org.apache.logging.log4j.LogManager


/**
  * Created by pp on 2017/5/15.
  */
object ConfigCache {

  val log = LogManager.getLogger

  val pro : Properties = new Properties()

  val list = List("hbase.properties","jdbc.properties")

  def getFilePath(fileName : String): String ={
    Thread.currentThread().getContextClassLoader.getResource(fileName).getPath
  }

  def loadConfig(): Unit ={
    list.foreach(a => {
      val properties = new Properties()
      properties.load(new FileInputStream(getFilePath(a)))
      pro.putAll(properties)
    })
  }

  def getProString(string: String,default: String): String ={
    pro.getProperty(string,default).toString
  }

  def getProInt(string: String,default: String):Int={
    pro.getProperty(string,default).toInt
  }

  def getProString(string: String) : String = {
    pro.getProperty(string).toString
  }

  def getProInt(string: String): Unit ={
    pro.getProperty(string)toInt
  }

  def main(args: Array[String]): Unit = {
    loadConfig
    println(ConfigCache.getProString("jdbc_url"))
  }

}
