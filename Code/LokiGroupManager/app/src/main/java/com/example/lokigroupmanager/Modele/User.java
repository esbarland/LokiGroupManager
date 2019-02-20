package com.example.lokigroupmanager.Modele;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 571475165L;

    private String firstname;
    private String surname;
    private String email;

    public User(String firstname, String surname, String email){
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return firstname + " - " + surname + " - " + email;
    }
}
