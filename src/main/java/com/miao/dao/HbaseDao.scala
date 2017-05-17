package com.miao.dao

import com.miao.common.utils.{ConfigCache, HTableManager}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor, TableName}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Scan}
import org.apache.hadoop.hbase.util.Bytes
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
  def createTable(tableName : String,family : List[String],numSplit : Int): Boolean ={
    val table : HTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName))
    family.foreach(name => table.addFamily(new HColumnDescriptor(name)))
    conn.getAdmin.createTable(table,splitKey(numSplit))
    log.info("create table "+ tableName + " is success !")
    true
  }

  def splitKey(numSplit : Int ) : Array[Array[Byte]] ={
    val keys = HTableManager.generatPartitionSeed(numSplit)
    keys.filter(p => !"00zz".contains(p)).map(f => f.getBytes)
  }

  def dropTable(tableName:String): Boolean ={
    val admin = conn.getAdmin
    if(tableIsExist(TableName.valueOf(tableName))){
      admin.disableTable(TableName.valueOf(tableName))
      admin.deleteTable(TableName.valueOf(tableName))
      log.info( tableName + " table is drop success ! ")
      true
    }else{
      log.info( tableName + " table is not exists ,can't drop table " )
      false
    }
  }

  def tableIsExist(tableName: TableName): Boolean ={
    val admin = conn.getAdmin
    admin.tableExists(tableName)
  }

  def rangeData(tableName : String,scan: Scan): Unit ={

    val table = conn.getTable(TableName.valueOf(tableName))
    val result = table.getScanner(scan)
    while(result.iterator().hasNext){
      val re = result.iterator().next()

    }

  }


}

object HbaseDao{

  val h = new HbaseDao
  ConfigCache.loadConfig()
  def main(args: Array[String]): Unit = {
    println(ConfigCache.getProString("hbase.zookeeper.quorum"))
    h.rangeData("H_TOPUP",new Scan())

  }
}
