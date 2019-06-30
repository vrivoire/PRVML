package com.vrivoire.prvml.model;

import java.sql.Timestamp;

/**
 *
 * @author Vincent
 */
public interface Booking {

    Timestamp getEndTime();

    Long getId();

    Timestamp getStartTime();

    void setEndTime(Timestamp endTime);

    void setStartTime(Timestamp startTime);

}
