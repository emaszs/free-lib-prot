package com.psilonsoft.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "insecure_user")
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 5250732319999950295L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "seq_inc_user_id", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    
    @Column(name="email", length = 200, nullable = false)
    private String email;

    @Column(name = "password_hash", length = 100, nullable = false)
    private String passwordHash;

    @Column(name="first_name", length = 200, nullable = false)
    private String firstName;

    @Column(name="last_name", length = 200, nullable = false)
    private String lastName;

    @Column(name = "password_salt", length = 100, nullable = true)
    private String passwordSalt;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(final String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(final String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", passwordHash=" + passwordHash + ", firstName=" + firstName + ", lastName=" + lastName
                + ", passwordSalt=" + passwordSalt + "]";
    }
}
