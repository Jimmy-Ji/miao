package com.miao.dao

import com.miao.common.utils.{ConfigCache, HTableManager}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor, TableName}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory}
import org.apache.logging.log4j.LogManager


/**
  * Created by pp on 2017/5/15.
  */
class HbaseDao {

  private val log = LogManager.getLogger

  private val conf : Configuration = HBaseConfiguration.create()
  conf.set("hbase.zookeeper.quorum",ConfigCache.getProString("hbase.zookeeper.quorum"))
  conf.set("hbase.zookeeper.property.clientPort",ConfigCache.getProString("hbase.zookeeper.property.clientPort"))
  conf.set("zookeeper.znode.parent",ConfigCache.getProString("zookeeper.znode.parent"))

  val conn : Connection = ConnectionFactory.createConnection(conf)

  /**
    * 建表可以并且带预分区的
    * @param tableName
    * @param family 列蔟名，多个
    * @param numSplit 预分区个数
    */
  def createTable(tableName : String,family : List[String],numSplit : Int): Unit ={
    val table : HTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName))
    family.foreach(name => table.addFamily(new HColumnDescriptor(name)))
    try{
      conn.getAdmin.createTable(table,splitKey(numSplit))
      log.info("create table "+ tableName + " is success !")
    }catch {
      case e => log.error(e.getMessage)
    }finally {
      if (conn != null) conn.close()
    }
  }



  def splitKey(numSplit : Int ) : Array[Array[Byte]] ={
    val keys = HTableManager.generatPartitionSeed(numSplit)
    keys.filter(p => !"00zz".contains(p)).map(f => f.getBytes)
  }



}

object HbaseDao{

  val h = new HbaseDao

  def main(args: Array[String]): Unit = {
    h.splitKey(5)
  }
}
