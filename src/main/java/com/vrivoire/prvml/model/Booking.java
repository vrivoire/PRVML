package com.vrivoire.prvml.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Vincent
 */
public interface Booking extends Comparable<Booking>, Serializable {

    Timestamp getEndTime();

    Long getId();

    Timestamp getStartTime();

    void setEndTime(Timestamp endTime);

    void setStartTime(Timestamp startTime);

}
