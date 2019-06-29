package com.vrivoire.prvml.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

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
public class Availability implements Comparable<Availability>, Serializable {

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

    public Availability() {
    }

    public Availability(Timestamp startTime, Timestamp endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.startTime);
        hash = 17 * hash + Objects.hashCode(this.endTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Availability other = (Availability) obj;
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.endTime, other.endTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Availability [");
        builder.append("endTime=").append(endTime);
        builder.append(", startTime=").append(startTime);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(Availability o) {
        return startTime.compareTo(o.startTime);
    }

}
