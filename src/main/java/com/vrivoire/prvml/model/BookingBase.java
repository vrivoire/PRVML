package com.vrivoire.prvml.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Vincent
 */
public abstract class BookingBase implements Booking, Comparable<BookingBase>, Serializable {

    private static final long serialVersionUID = 7451119007133245196L;

    @Override
    public int compareTo(BookingBase o) {
        return getStartTime().compareTo(o.getStartTime());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(getStartTime());
        hash = 17 * hash + Objects.hashCode(getEndTime());
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
        final BookingBase other = (BookingBase) obj;
        if (!Objects.equals(getStartTime(), other.getStartTime())) {
            return false;
        }
        return Objects.equals(getEndTime(), other.getEndTime());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName()).append(" [");
        builder.append("id=").append(getId());
        builder.append(", endTime=").append(getStartTime());
        builder.append(", startTime=").append(getEndTime());
        builder.append("]");
        return builder.toString();
    }
}
