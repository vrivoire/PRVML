package com.vrivoire.prvml.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Vincent
 */
@Entity
@Table(name = "availability")
public class Availability extends BookingBase {

    private static final long serialVersionUID = -4668845200484950595L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @NotNull(message = "The start date cannot be empty")
    @Column(name = "START_TIME")
    private Timestamp startTime;

    @NotNull(message = "The end date cannot be empty")
    @Column(name = "END_TIME")
    private Timestamp endTime;

    /**
     *
     */
    public Availability() {
    }

    /**
     *
     * @param startTime
     * @param endTime
     */
    public Availability(Timestamp startTime, Timestamp endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     *
     * @return
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    @Override
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime
     */
    @Override
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return
     */
    @Override
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime
     */
    @Override
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

}
