package com.bimbr.clisson.protocol;

import static com.bimbr.clisson.protocol.Json.jsonFor;
import static com.bimbr.clisson.protocol.Types.id;
import static com.bimbr.clisson.util.Arguments.nonEmpty;
import static com.bimbr.clisson.util.Arguments.nonNull;
import static com.bimbr.clisson.util.Arguments.setWithNonNullElements;
import static com.bimbr.clisson.util.Collections.immutableCopyOf;

import java.util.Date;
import java.util.Set;

/**
 * An event that occurred during message processing.
 * 
 * @author mmakowski
 * @since 1.0.0
 */
public class Event implements StandaloneObject {
    private final String sourceId;
    private final Date timestamp;
    private final Set<String> inputMessageIds;
    private final Set<String> outputMessageIds;
    private final String description;

    /**
     * @param sourceId the id of the source of the event; must not be empty
     * @param timestamp the time the event occurred; must not be {@code null}
     * @param inputMessageIds a set containing ids of the input messages; can be empty
     * @param outputMessageIds a set containing ids of the output messages; can be empty
     * @param description the description of the event; must not be {@code null}
     * The union of {@code inputMessagesIds} and {@code outputMessageIds} must have at least one element  
     * @since 1.0.0
     */
    public Event(final String      sourceId,
                 final Date        timestamp,
                 final Set<String> inputMessageIds,
                 final Set<String> outputMessageIds,
                 final String      description) {
        this.sourceId         = nonEmpty(sourceId, "sourceId");
        this.timestamp        = new Date(nonNull(timestamp, "timestamp").getTime());
        this.inputMessageIds  = immutableCopyOf(setWithNonNullElements(inputMessageIds, "inputMessageIds"));
        this.outputMessageIds = immutableCopyOf(setWithNonNullElements(outputMessageIds, "outputMessageIds"));
        this.description      = nonNull(description, "description");
        if (inputMessageIds.size() + outputMessageIds.size() == 0) 
            throw new IllegalArgumentException("either inputMessageIds or outputMessageIds must be non-empty");
    }

    /**
     * @return the id of the event source
     * @since 1.0.0
     */
    public String getSourceId() {
        return sourceId;
    }
    
    /**
     * @return the time the event occurred
     * @since 1.0.0
     */
    public Date getTimestamp() {
        return new Date(timestamp.getTime());
    }
    
    /**
     * @return the ids of the input messages
     * @since 1.0.0
     */
    public Set<String> getInputMessageIds() {
        return inputMessageIds;
    }
    
    /**
     * @return the ids of output messages
     * @since 1.0.0
     */
    public Set<String> getOutputMessageIds() {
        return outputMessageIds;
    }
    
    /**
     * @return the description of the split
     * @since 1.0.0
     */
    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + sourceId.hashCode();
        result = prime * result + timestamp.hashCode();
        result = prime * result + inputMessageIds.hashCode();
        result = prime * result + outputMessageIds.hashCode();
        result = prime * result + description.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Event that = (Event) obj;
        return sourceId.equals(that.sourceId) &&
               timestamp.equals(that.timestamp) &&
               inputMessageIds.equals(that.inputMessageIds) &&
               outputMessageIds.equals(that.outputMessageIds) &&
               description.equals(that.description);
    }
    
    @Override
    public String toString() {
        return id(getClass()) + " " + jsonFor(this);
    }
}
