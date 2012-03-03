package com.bimbr.clisson.protocol
import java.util.Date

import java.lang.{ Long => JLong }
import java.util.{ Set => JSet }
import scala.collection.mutable.{ Buffer, Map, Set }
import scala.collection.JavaConversions._

import org.junit.runner.RunWith
import org.specs2.matcher.Matcher
import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.specs2.runner.JUnitRunner

import com.bimbr.clisson.protocol.Json._

@RunWith(classOf[JUnitRunner])
class TrailSpec extends Specification {
  "Trail construction" should {
    
    "require non-null event map" in {
      new Trail(null, EmptyEventGraph, EmptyInitialEventIds) must throwAn [IllegalArgumentException]
    }
    
    "require non-null event graph" in {
      new Trail(Events, null, EmptyInitialEventIds) must throwAn [IllegalArgumentException]
    } 
    
    "require non-null initial event id list" in {
      new Trail(Events, EmptyEventGraph, null) must throwAn [IllegalArgumentException]
    }
    
    "require that there are no null keys in event map" in {
      new Trail(EventsWithNullKey, EmptyEventGraph, EmptyInitialEventIds) must throwAn [IllegalArgumentException]
    }
    
    "require that there are no null values in event map" in {
      new Trail(EventsWithNullValue, EmptyEventGraph, EmptyInitialEventIds) must throwAn [IllegalArgumentException]
    }
    
    "require that there are no null keys in event graph" in {
      new Trail(Events, EventGraphWithNullKey, EmptyInitialEventIds) must throwAn [IllegalArgumentException]
    }
    
    "require that there are no null values in event graph" in {
      new Trail(Events, EventGraphWithNullValue, EmptyInitialEventIds) must throwAn [IllegalArgumentException]
    }
    
    "require that there are no null values in initial event id list" in {
      new Trail(Events, EmptyEventGraph, InitialEventIdsWithNull) must throwAn [IllegalArgumentException]
    }
    
    "require that all event ids referenced from event graph keys are defined" in {
      new Trail(Map[JLong, Event]((2L, mocks.Event)), EventGraph, EmptyInitialEventIds) must throwAn [IllegalArgumentException]
    }

    "require that all event ids referenced from event graph values are defined" in {
      new Trail(Map[JLong, Event]((1L, mocks.Event)), EventGraph, EmptyInitialEventIds) must throwAn [IllegalArgumentException]
    }
    
    "require that all event ids referenced from initial event id list are defined" in {
      new Trail(EmptyEvents, EmptyEventGraph, InitialEventIds) must throwAn [IllegalArgumentException]
    }
  }
  
  "Trail" should {
    "provide chronological timeline" in {
      Trail.getTimeline.toSeq must (beInChronologicalOrder and containAllOf(Events.values.toSeq))
    }
  }

  "Trail JSON serialisation" should {
    "be reversible" in {
      // should not use mocks here
      val events = Map[JLong, Event]((1, new Checkpoint(new EventHeader("test", ts("2012-03-01 12:00:00.001"), 1), "msg-1", "e1")),
                                     (2, new Checkpoint(new EventHeader("test", ts("2012-03-02 12:01:00.001"), 1), "msg-1", "e2")),
                                     (3, new Checkpoint(new EventHeader("test", ts("2012-03-03 13:01:00.001"), 1), "msg-1", "e3")))
      val trail = new Trail(events, EventGraph, InitialEventIds)
      
      fromJson(jsonFor(trail), classOf[Trail]) mustEqual trail
    }
  }
  
  private def beInChronologicalOrder: Matcher[Traversable[Event]] = ({ events: Traversable[Event] =>
      val eventList = events.toList
      def timestamp(e: Event) = e.getEventHeader.getTimestamp
      eventList.map(timestamp).sorted == eventList.map(timestamp)
    }, 
    "is not in chronological order")
  
    
  private def ts(timestampStr: String) = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(timestampStr)
  
  private object mocks extends Mockito {
    val Event = mock[Event]
    
    def eventWithTimestamp(timestampStr: String) = {
      val event = mock[Event]
      val header = new EventHeader("id", ts(timestampStr), 1)
      event.getEventHeader returns header
      event
    }
  }

  // pathological trail components
  val EmptyEvents             = Map[JLong, Event]()
  val EventsWithNullKey       = Map[JLong, Event]((null, mocks.Event))
  val EventsWithNullValue     = Map[JLong, Event]((1, null))
  val EmptyEventGraph         = Map[JLong, JSet[JLong]]()
  val EventGraphWithNullKey   = Map[JLong, JSet[JLong]]((null, Set[JLong](2)))
  val EventGraphWithNullValue = Map[JLong, JSet[JLong]]((1, null))
  val EmptyInitialEventIds    = Set[JLong]()
  val InitialEventIdsWithNull = Buffer[JLong](1, null).toSet
  
  // complete trail (mock based)
  val Events                  = Map[JLong, Event]((3, mocks.eventWithTimestamp("2012-03-03 16:32:18.133")),
                                                  (1, mocks.eventWithTimestamp("2012-03-01 16:32:18.133")),
                                                  (2, mocks.eventWithTimestamp("2012-03-02 16:32:18.133")))
  val EventGraph              = Map[JLong, JSet[JLong]]((1, Set[JLong](2, 3)),
                                                        (2, Set[JLong](3)))
  val InitialEventIds         = Set[JLong](1)
  val Trail                   = new Trail(Events, EventGraph, InitialEventIds)
}