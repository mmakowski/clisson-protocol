package com.bimbr.clisson.protocol;

import static com.bimbr.clisson.protocol.Json.jsonFor;
import static com.bimbr.clisson.protocol.Types.id;
import static com.bimbr.clisson.util.Arguments.nonEmpty;
import static com.bimbr.clisson.util.Arguments.nonNull;
import static com.bimbr.clisson.util.Arguments.setWithNonNullElements;
import static com.bimbr.clisson.util.Collections.immutableCopyOf;

import java.util.Set;

/**
 * An event that occurs when a message being split into a number of messages.
 * 
 * @author mmakowski
 * @since 1.0.0
 */
public class Split implements Event {
    private final EventHeader eventHeader;
    private final String inputMessageId;
    private final Set<String> outputMessageIds;
    private final String description;

    /**
     * @param eventHeader the header of this event; must not be {@code null}
     * @param inputMessageId the id of the input message; must not be empty
     * @param outputMessageIds a set containing ids of the output messages; must not be empty
     * @param description the description of the split; must not be {@code null}
     * @since 1.0.0
     */
    public Split(final EventHeader eventHeader, 
                 final String      inputMessageId,
                 final Set<String> outputMessageIds,
                 final String      description) {
        this.eventHeader      = nonNull(eventHeader, "eventHeader");
        this.inputMessageId   = nonEmpty(inputMessageId, "inputMessageId");
        this.outputMessageIds = immutableCopyOf(nonEmpty(setWithNonNullElements(outputMessageIds, "outputMessageIds"), "outputMessageIds"));
        this.description      = nonNull(description, "description");
    }

    /**
     * @return the header of this event
     * @since 1.0.0
     */
    public EventHeader getEventHeader() {
        return eventHeader;
    }
    
    /**
     * @return the id of the input message
     * @since 1.0.0
     */
    public String getInputMessageId() {
        return inputMessageId;
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
        result = prime * result + eventHeader.hashCode();
        result = prime * result + inputMessageId.hashCode();
        result = prime * result + outputMessageIds.hashCode();
        result = prime * result + description.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Split that = (Split) obj;
        return eventHeader.equals(that.eventHeader) &&
               inputMessageId.equals(that.inputMessageId) &&
               outputMessageIds.equals(that.outputMessageIds) &&
               description.equals(that.description);
    }
    
    @Override
    public String toString() {
        return id(getClass()) + " " + jsonFor(this);
    }
}
