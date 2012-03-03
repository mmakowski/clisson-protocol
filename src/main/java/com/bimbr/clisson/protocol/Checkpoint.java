package com.bimbr.clisson.protocol;

import static com.bimbr.clisson.protocol.Json.jsonFor;
import static com.bimbr.clisson.protocol.Types.id;
import static com.bimbr.clisson.util.Arguments.nonEmpty;
import static com.bimbr.clisson.util.Arguments.nonNull;

/**
 * Checkpoint event records that a message has arrived at certain point of processing.
 *
 * @author mmakowski
 * @since 1.0.0
 */
public final class Checkpoint implements Event {
    private final EventHeader eventHeader;
    private final String messageId;
    private final String description;

    /**
     * @param eventHeader the header of this event; must not be {@code null}
     * @param messageId the id of the message that arrived at the checkpoint; must not be empty
     * @param description the description of the checkpoint; must not be {@code null}
     * @since 1.0.0
     */
    public Checkpoint(final EventHeader eventHeader, 
                      final String      messageId,
                      final String      description) {
        this.eventHeader = nonNull(eventHeader, "eventHeader");
        this.messageId   = nonEmpty(messageId, "messageId");
        this.description = nonNull(description, "description");
    }

    /**
     * @return the header of this event
     * @since 1.0.0
     */
    public EventHeader getEventHeader() {
        return eventHeader;
    }
    
    /**
     * @return the id of the message that arrived at the checkpoint
     * @since 1.0.0
     */
    public String getMessageId() {
        return messageId;
    }
    
    /**
     * @return the description of the checkpoint
     * @since 1.0.0
     */
    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + description.hashCode();
        result = prime * result + eventHeader.hashCode();
        result = prime * result + messageId.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Checkpoint that = (Checkpoint) obj;
        return eventHeader.equals(that.eventHeader) &&
               messageId.equals(that.messageId) &&
               description.equals(that.description);
    }
    
    @Override
    public String toString() {
        return id(getClass()) + " " + jsonFor(this);
    }
}