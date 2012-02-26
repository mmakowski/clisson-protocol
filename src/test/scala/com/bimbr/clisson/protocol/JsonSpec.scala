package com.bimbr.clisson.protocol

import java.util.Date

import org.specs2.mutable._

import com.bimbr.clisson.protocol.Json._

class JsonSpec extends Specification {
  "Json" should {
    
    "disallow null argument to `jsonFor`" in {
      jsonFor(null) must throwAn [IllegalArgumentException]
    }
    
    "disallow empty json argument to `fromJson`" in {
      fromJson("", classOf[Object]) must throwAn [IllegalArgumentException]
    }
  }
}