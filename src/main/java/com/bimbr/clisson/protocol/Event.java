package com.bimbr.clisson.protocol;

/**
 * @author mmakowski
 * @since 1.0.0
 */
public interface Event {
    /**
     * @return header containing event metadata
     */
    EventHeader getEventHeader();
}
