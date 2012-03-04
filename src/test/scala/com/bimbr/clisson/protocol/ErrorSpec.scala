package com.bimbr.clisson.protocol

import java.util.Date

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

import com.bimbr.clisson.protocol.Json._

@RunWith(classOf[JUnitRunner])
class ErrorSpec extends Specification {
  "Error construction" should {
    "require non-null header" in {
      new Error(null, MsgId, Description) must throwAn [IllegalArgumentException]
    }
    
    "require non-empty message id" in {
      new Error(Header, "", Description) must throwAn [IllegalArgumentException]
    }
  }
  
  "Error JSON serialisation" should {
    "be reversible" in {
      fromJson(jsonFor(Error), classOf[Error]) mustEqual Error
    }
  }

  val Header = new EventHeader("compId", new Date, 0)
  val MsgId = "msgId"
  val Description = "desc"
  val Error = new Error(Header, MsgId, Description) 
}