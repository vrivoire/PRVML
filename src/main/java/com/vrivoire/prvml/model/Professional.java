package com.vrivoire.prvml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Vincent
 */
@Entity
@Table(name = "professional")
public class Professional implements Comparable<Professional>, Serializable {

    private static final long serialVersionUID = 6602447071846638781L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @NotNull(message = "The first name cannot be empty")
    @Size(min = 3, max = 200, message = "The first name must be between 3 and 200 characters")
    @NotBlank(message = "The first mame cannot be empty")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull(message = "The last name cannot be empty")
    @Size(min = 3, max = 200, message = "The first name must be between 3 and 200 characters")
    @NotBlank(message = "The last mame cannot be empty")
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotNull
    @Size(min = 6, max = 400)
    @NotBlank
    @Column(name = "UNIQUE_ID", unique = true)
    private String uniqueId;

    @NotNull
    @ManyToOne
    private Clinic clinic;

    @OneToMany
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany
    private List<Availability> availabilities = new ArrayList<>();

    public Professional(String firstName, String lastName, Clinic clinic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.clinic = clinic;
        generateUniqueId();
    }

    public Professional() {
    }

    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    public Long getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public List<Appointment> getAppointments() {
        Collections.sort(appointments);
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void addAppointment(Appointment appointment) throws ValidationException {
        Validator.validateBookings(appointment, appointments, this);
        appointments.add(appointment);
    }

    public List<Availability> getAvailabilities() {
        Collections.sort(availabilities);
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public void addAvailability(Availability availability) throws ValidationException {
        Validator.validateBookings(availability, availabilities, this);
        availabilities.add(availability);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        generateUniqueId();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        generateUniqueId();
    }

    public String getUniqId() {
        return uniqueId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.firstName);
        hash = 97 * hash + Objects.hashCode(this.lastName);
        hash = 97 * hash + Objects.hashCode(this.uniqueId);
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
        final Professional other = (Professional) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.uniqueId, other.uniqueId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Professional [");
        builder.append("id=").append(id);
        builder.append(", firstName=").append(firstName);
        builder.append(", lastName=").append(lastName);
        builder.append(", uniqueId=").append(uniqueId);
        builder.append(", clinic=").append(clinic);
        builder.append(", appointments=").append(appointments);
        builder.append(", availabilities=").append(availabilities);
        builder.append("]");
        return builder.toString();
    }

    private void generateUniqueId() {
        uniqueId = "";
        if (firstName != null) {
            uniqueId = firstName;
        }
        if (lastName != null) {
            uniqueId += lastName;
        }

    }

    @Override
    public int compareTo(Professional o) {
        return uniqueId.compareTo(o.uniqueId);
    }

}
