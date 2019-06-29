package com.vrivoire.prvml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Vincent
 */
@Entity
@Table(name = "patient")
public class Patient implements Comparable<Patient>, Serializable {

    private static final long serialVersionUID = -5803551043783035609L;

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

    @OneToMany
    private List<Appointment> appointments = new ArrayList<>();

    @NotNull
    @ManyToOne
    private Clinic clinic;

    public Patient(String firstName, String lastName, Clinic clinic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.clinic = clinic;
        generateUniqueId();
    }

    public Patient() {
    }

    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void addAppointment(Appointment appointment) throws Exception {
        if (appointments.contains(appointment)) {
            throw new Exception("The time slot is already taken for: " + appointment + " and " + this);
        }
        appointments.add(appointment);
    }

    public Long getId() {
        return id;
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
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.firstName);
        hash = 97 * hash + Objects.hashCode(this.lastName);
        hash = 97 * hash + Objects.hashCode(this.uniqueId);
        hash = 97 * hash + Objects.hashCode(this.appointments);
        hash = 97 * hash + Objects.hashCode(this.clinic);
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
        final Patient other = (Patient) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.uniqueId, other.uniqueId)) {
            return false;
        }
        if (!Objects.equals(this.appointments, other.appointments)) {
            return false;
        }
        if (!Objects.equals(this.clinic, other.clinic)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Patient [");
        builder.append("id=").append(id);
        builder.append(", firstName=").append(firstName);
        builder.append(", lastName=").append(lastName);
        builder.append(", uniqueId=").append(uniqueId);
        builder.append(", clinic=").append(clinic);
        builder.append(", appointments=").append(appointments);
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
    public int compareTo(Patient o) {
        return uniqueId.compareTo(uniqueId);
    }
}
