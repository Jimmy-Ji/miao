package com.miao.dao

import com.miao.common.utils.{ConfigCache, HTableManager}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase._
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Scan}
import org.apache.hadoop.hbase.filter.PageFilter
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

  /**
    * 根据分区数获取预分区种子
    * @param numSplit
    * @return
    */
  private def splitKey(numSplit : Int ) : Array[Array[Byte]] ={
    val keys = HTableManager.generatPartitionSeed(numSplit)
    keys.filter(p => !"00zz".contains(p)).map(f => f.getBytes)
  }

  /**
    * 删除表
    * @param tableName
    * @return
    */
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

  def rangeData[T](tableName : String,scan: Scan): Unit ={
    val table = conn.getTable(TableName.valueOf(tableName))
    scan.setFilter(new PageFilter(1))
    val result = table.getScanner(scan)
    val it = result.iterator()
    while(it.hasNext){
      val re = it.next()
      re.getColumnCells(Bytes.toBytes("f1"),Bytes.toBytes("last_model")).forEach(a => println(new String(CellUtil.cloneValue(a)) ))
    }

  }

  def test[T](c : Class[T]): List[(String,String)] ={
    c.getDeclaredFields.map(a => {
      List((a.getName,a.getType.getSimpleName))
    }).reduce((a,b) => a++b)
  }


}

object HbaseDao{

  val h = new HbaseDao
  ConfigCache.loadConfig()
  case class Result1(name : String,age:Int)

  def main(args: Array[String]): Unit = {

    //h.test(classOf[Result1]).foreach(a=>println(a))
    h.rangeData("wide_table",new Scan())

  }
}
