package com.miao.service

import com.miao.common.base.BaseService
import com.miao.model.User
import org.springframework.stereotype.Service

/**
  * Created by pp on 2017/5/2.
  */
@Service
class UserService extends BaseService[String,User]{


  override def save(model: User): Unit = {

  }
}
