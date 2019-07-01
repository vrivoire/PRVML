package com.vrivoire.prvml.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Vincent
 */
public interface Booking extends Comparable<Booking>, Serializable {

    /**
     *
     * @return
     */
    Timestamp getEndTime();

    /**
     *
     * @return
     */
    Long getId();

    /**
     *
     * @return
     */
    Timestamp getStartTime();

    /**
     *
     * @param endTime
     */
    void setEndTime(Timestamp endTime);

    /**
     *
     * @param startTime
     */
    void setStartTime(Timestamp startTime);

}
