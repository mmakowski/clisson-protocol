package com.bimbr.clisson.protocol;

import static com.bimbr.clisson.protocol.Json.jsonFor;
import static com.bimbr.clisson.protocol.Types.id;
import static com.bimbr.clisson.util.Arguments.setWithNonNullElements;
import static com.bimbr.clisson.util.Arguments.mapWithNonNullKeysAndValues;
import static com.bimbr.clisson.util.Collections.immutableCopyOf;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Trail of a message. Contains all events related to specified message id. Events are identified by ids and are arranged in a directed
 * acyclic graph. It is required that event ids on any path in this graph are increasing.
 * 
 * @author mmakowski
 * @since 1.0.0
 */
public final class Trail implements StandaloneObject {
    // event id -> event
    private final Map<Long, Event>     events;
    // event id -> subsequent event ids
    private final Map<Long, Set<Long>> eventGraph;
    private final Set<Long>            initialEventIds;
    
    /**
     * @param events a map mapping event ids to event objects
     * @param eventGraph a map mapping event ids to lists of ids of events that occured immediately after
     * @param initialEventIds list of event ids that start the trail
     * @since 1.0.0
     */
    public Trail(final Map<Long, Event>     events,
                 final Map<Long, Set<Long>> eventGraph,
                 final Set<Long>            initialEventIds) {
        this.events          = immutableCopyOf(mapWithNonNullKeysAndValues(events, "events"));
        this.eventGraph      = deeplyImmutableCopyOf(mapWithNonNullKeysAndValues(eventGraph, "eventGraph"));
        this.initialEventIds = immutableCopyOf(setWithNonNullElements(initialEventIds, "initialEventIds"));
        assertEventsDefined(eventGraph.keySet(), "event graph keys");
        for (Set<Long> eventIdSet: eventGraph.values()) assertEventsDefined(eventIdSet, "event graph values");
        assertEventsDefined(initialEventIds, "initial event list");
    }
    
    private static Map<Long, Set<Long>> deeplyImmutableCopyOf(Map<Long, Set<Long>> source) {
        final Map<Long, Set<Long>> copy = new HashMap<Long, Set<Long>>(source.size());
        for (Map.Entry<Long, Set<Long>> entry : source.entrySet()) {
            copy.put(entry.getKey(), immutableCopyOf(entry.getValue()));
        }
        return java.util.Collections.unmodifiableMap(copy);
    }

    private void assertEventsDefined(Iterable<Long> eventIds, String source) {
        for (long eventId : eventIds) {
            if (!events.containsKey(eventId)) throw new IllegalArgumentException("id of undefined event found in " + source + ": " + eventId);
        }
    }
    
    /**
     * @return list of all events in the trail arranged by timestamp, ascending
     * @since 1.0.0
     */
    public List<Event> getTimeline() {
        final ArrayList<Event> timeline = new ArrayList<Event>(events.size());
        timeline.addAll(events.values());
        Collections.sort(timeline, timestampComparator);
        return unmodifiableList(timeline);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Trail that = (Trail) obj;
        return events.equals(that.events) &&
               eventGraph.equals(that.eventGraph) &&
               initialEventIds.equals(that.initialEventIds);
    }
    
    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result = prime * result + events.hashCode();
        result = prime * result + eventGraph.hashCode();
        result = prime * result + initialEventIds.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return id(getClass()) + " " + jsonFor(this);
    }
    
    private static Comparator<Event> timestampComparator = new Comparator<Event>() {
        public int compare(Event e1, Event e2) {
            return e1.getTimestamp().compareTo(e2.getTimestamp());
        }
    };
}
