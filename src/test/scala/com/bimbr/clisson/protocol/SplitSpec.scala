package com.bimbr.clisson.protocol

import java.util.Date
import scala.collection.JavaConversions._

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

import com.bimbr.clisson.protocol.Json._

@RunWith(classOf[JUnitRunner])
class SplitSpec extends Specification {
  "Split construction" should {
    "require non-null header" in {
      new Split(null, InputMsgId, OutputMsgIds, Description) must throwAn [IllegalArgumentException]
    }
    "require non-empty input message id" in {
      new Split(Header, "", OutputMsgIds, Description) must throwAn [IllegalArgumentException]
    }
    "require non-empty set of output message ids" in {
      new Split(Header, InputMsgId, Set[String](), Description) must throwAn [IllegalArgumentException]
    }
    "disallow nulls in output message id set" in {
      new Split(Header, InputMsgId, Set[String]("msg-2", null), Description) must throwAn [IllegalArgumentException]
    }
  }
  "Split JSON serialisation" should {
    "be reversible" in {
      fromJson(jsonFor(Split), classOf[Split]) mustEqual Split
    }
  }

  val Header = new EventHeader("compId", new Date, 0)
  val InputMsgId = "msg-1"
  val OutputMsgIds = Set("msg-1", "msg-2")
  val Description = "desc"
  val Split = new Split(Header, InputMsgId, OutputMsgIds, Description) 
}