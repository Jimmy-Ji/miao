package com.miao.common.base

import org.springframework.stereotype.Service

/**
  * Created by pp on 2017/5/2.
  */
@Service
trait BaseService [K,V]{

  def save(model : V)

}
