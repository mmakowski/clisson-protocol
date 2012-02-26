package com.bimbr.clisson.protocol

import java.util.Date

import org.specs2.mutable._

import com.bimbr.clisson.protocol.Json._

class CheckpointSpec extends Specification {
  "Checkpoint" should {
    
    "require non-null header on construction" in {
      new Checkpoint(null, MsgId, Description) must throwAn [IllegalArgumentException]
    }
    
    "require non-empty message id on construction" in {
      new Checkpoint(Header, "", Description) must throwAn [IllegalArgumentException]
    }
    
    "serialise and then deserialise to equal object" in {
      fromJson(jsonFor(Checkpoint), classOf[Checkpoint]) mustEqual Checkpoint
    }
  }

  val Header = new EventHeader("compId", new Date, 0)
  val MsgId = "msgId"
  val Description = "desc"
  val Checkpoint = new Checkpoint(Header, MsgId, Description) 
}