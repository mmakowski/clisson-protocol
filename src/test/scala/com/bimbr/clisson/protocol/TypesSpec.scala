package com.bimbr.clisson.protocol

import java.util.Date

import org.specs2.mutable._

import com.bimbr.clisson.protocol.Types._

class TypesSpec extends Specification {
  "Types" should {
    
    "throw `IllegalArgumentException` for usnupported type name" in {
      classFor("unknowntype").asInstanceOf[Class[Any]] must throwAn [IllegalArgumentException]
    }
  }
}