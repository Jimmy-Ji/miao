package com.miao.model

import java.sql.ResultSet

import org.springframework.jdbc.core.RowMapper

import scala.beans.BeanProperty

/**
  * Created by pp on 2017/5/2.
  */

class User extends Serializable{
  @BeanProperty var id : String = _
  @BeanProperty var name : String = _
  @BeanProperty var sex : String = _
}
