package com.bimbr.clisson.protocol

import java.util.Date
import scala.collection.JavaConversions._

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

import com.bimbr.clisson.protocol.Json._

@RunWith(classOf[JUnitRunner])
class EventSpec extends Specification {
  "Event construction" should {
    "require non-null timestamp" in {
      new Event(Source, null, InputMsgIds, OutputMsgIds, Description) must throwAn [IllegalArgumentException]
    }
    "allow empty set of input messages" in {
      new Event(Source, Timestamp, EmptySet, OutputMsgIds, Description).getInputMessageIds must beEmpty
    }
    "allow empty set of output messages" in {
      new Event(Source, Timestamp, InputMsgIds, EmptySet, Description).getOutputMessageIds must beEmpty
    }
    "require that the union of input and output message ids is not empty" in {
      new Event(Source, Timestamp, EmptySet, EmptySet, Description) must throwAn [IllegalArgumentException]
    }
    "disallow nulls in input message id set" in {
      new Event(Source, Timestamp, Set("msg-2", null), OutputMsgIds, Description) must throwAn [IllegalArgumentException]
    }
    "disallow nulls in output message id set" in {
      new Event(Source, Timestamp, InputMsgIds, Set("msg-2", null), Description) must throwAn [IllegalArgumentException]
    }
  }
  "Event JSON serialisation" should {
    "be reversible" in {
      fromJson(jsonFor(Event), classOf[Event]) mustEqual Event
    }
  }

  val Source = "compId"
  val Timestamp = new Date
  val InputMsgIds = Set("msg-1", "msg-2")
  val OutputMsgIds = Set("msg-1", "msg-3")
  val EmptySet = Set[String]()
  val Description = "desc"
  val Event = new Event(Source, Timestamp, InputMsgIds, OutputMsgIds, Description) 
}