package com.bimbr.clisson.protocol

import java.util.Date

import com.bimbr.clisson.protocol.Json._

import org.specs2.mutable._

class EventHeaderSpec extends Specification {
  "EventHeader construction" should {
    "require non-empty component id on construction" in {
      new EventHeader("", Timestamp, Priority) must throwAn [IllegalArgumentException]
    }
    
    "require non-null timeestamp on construction" in {
      new EventHeader(SrcId, null, Priority) must throwAn [IllegalArgumentException]
    }
  }
  "EventHeader serialisation" should {
    "be reversible" in {
      fromJson(jsonFor(Header), classOf[EventHeader]) mustEqual Header
    }
  }

  val SrcId = "compId"
  val Timestamp = new Date
  val Priority = 0
  val Header = new EventHeader(SrcId, Timestamp, Priority)
}