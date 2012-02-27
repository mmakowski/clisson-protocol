package com.bimbr.clisson.protocol;

/**
 * @author mmakowski
 * @since 1.0.0
 */
public interface Event extends StandaloneObject {
    /**
     * @return header containing event metadata
     */
    EventHeader getEventHeader();
}
