package com.vrivoire.prvml.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Vincent
 */
@Entity
@Table(name = "clinic")
public class Clinic implements Comparable<Clinic>, Serializable {

    private static final long serialVersionUID = 7144791867622755443L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @NotNull(message = "The clinic's mame cannot be empty")
    @Size(min = 3, max = 200, message = "The clinic's mame must be between 3 and 200 characters")
    @NotBlank(message = "The clinic's mame cannot be empty")
    @Column(name = "NAME", unique = true)
    private String name;

    /**
     *
     */
    public Clinic() {
    }

    /**
     *
     * @param name
     */
    public Clinic(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Clinic [");
        builder.append("id=").append(id);
        builder.append(", name=").append(name);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
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
        final Clinic other = (Clinic) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Clinic o) {
        return name.compareTo(o.name);
    }

}
