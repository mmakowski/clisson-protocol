package com.bimbr.clisson.protocol

import java.util.Date

import org.specs2.mutable._

import com.bimbr.clisson.protocol.Json._

class CheckpointEventSpec extends Specification {
  "CheckpointEvent" should {
    
    "require non-null header on construction" in {
      new CheckpointEvent(null, MsgId, Description) must throwAn [IllegalArgumentException]
    }
    
    "require non-empty message id on construction" in {
      new CheckpointEvent(Header, "", Description) must throwAn [IllegalArgumentException]
    }
    
    "serialise and then deserialise to equal object" in {
      fromJson(jsonFor(Event), classOf[CheckpointEvent]) mustEqual Event
    }
  }

  val Header = new EventHeader("compId", new Date, 0)
  val MsgId = "msgId"
  val Description = "desc"
  val Event = new CheckpointEvent(Header, MsgId, Description) 
}