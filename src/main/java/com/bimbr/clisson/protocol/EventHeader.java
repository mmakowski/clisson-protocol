package com.bimbr.clisson.protocol;

import static com.bimbr.clisson.protocol.Json.jsonFor;
import static com.bimbr.clisson.util.Arguments.nonEmpty;
import static com.bimbr.clisson.util.Arguments.nonNull;

import java.util.Date;

/**
 * Class containing metadata about an event.
 *
 * @author mmakowski
 * @since 1.0.0
 */
public final class EventHeader {
    private final String sourceId;
    private final Date timestamp;
    private final int priority;

    /**
     * @param sourceId the component that generated event; must not be empty
     * @param timestamp the time of the event; must not be null
     * @param priority the priority of the event
     * @since 1.0.0
     */
    public EventHeader(final String sourceId, final Date timestamp, final int priority) {
        this.sourceId = nonEmpty(sourceId, "sourceId");
        this.timestamp = new Date(nonNull(timestamp, "timestamp").getTime());
        this.priority = priority;
    }

    /**
     * @return the id of the component that generated event
     * @since 1.0.0
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * @return the time of the event
     * @since 1.0.0
     */
    public Date getTimestamp() {
        return new Date(timestamp.getTime());
    }

    /**
     * @return the priority of the event
     * @since 1.0.0
     */
    public int getPriority() {
        return priority;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + priority;
        result = prime * result + sourceId.hashCode();
        result = prime * result + timestamp.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EventHeader that = (EventHeader) obj;
        return sourceId.equals(that.sourceId) &&
               timestamp.equals(that.timestamp) &&
               priority == that.priority;
    }

    @Override
    public String toString() {
        return "EventHeader " + jsonFor(this);
    }
}
