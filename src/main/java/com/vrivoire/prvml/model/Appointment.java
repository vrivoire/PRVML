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
@Table(name = "appointment")
public class Appointment extends BookingBase {

    private static final long serialVersionUID = 8032335199037289491L;

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

    public Appointment() {
    }

    public Appointment(Timestamp startTime, Timestamp endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Timestamp getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Override
    public Timestamp getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
