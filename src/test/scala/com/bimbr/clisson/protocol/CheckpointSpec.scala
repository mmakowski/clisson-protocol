package com.bimbr.clisson.protocol

import java.util.Date

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

import com.bimbr.clisson.protocol.Json._

@RunWith(classOf[JUnitRunner])
class CheckpointSpec extends Specification {
  "Checkpoint construction" should {
    
    "require non-null header" in {
      new Checkpoint(null, MsgId, Description) must throwAn [IllegalArgumentException]
    }
    
    "require non-empty message id" in {
      new Checkpoint(Header, "", Description) must throwAn [IllegalArgumentException]
    }
  }
  
  "Checkpoint JSON serialisation" should {
    "be reversible" in {
      fromJson(jsonFor(Checkpoint), classOf[Checkpoint]) mustEqual Checkpoint
    }
  }

  val Header = new EventHeader("compId", new Date, 0)
  val MsgId = "msgId"
  val Description = "desc"
  val Checkpoint = new Checkpoint(Header, MsgId, Description) 
}