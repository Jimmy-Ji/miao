package com.miao.common.page


import scala.beans.BeanProperty
import scala.reflect.ClassTag

/**
  * Created by root on 17-5-9.
  */
class PageInfo[V:ClassTag] extends Serializable{

  @BeanProperty var count : Int = _
  @BeanProperty var pageCount : Int = _
  @BeanProperty var currentPage : Int = _
  @BeanProperty var list : List[V] = _

}
