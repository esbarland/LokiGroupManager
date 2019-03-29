package com.example.lokigroupmanager.Model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable, Comparable<User> {
    private static final long serialVersionUID = 571475165L;

    private boolean isSelected = false;
    private String firstname;
    private String surname;
    private String email;

    public User(String firstname, String surname, String email) {
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

    public void setSelected(boolean state){
        isSelected = state;
    }

    public boolean selected() {
        return isSelected;
    }

    @Override
    public int compareTo(@NonNull User o) { // MOCHE !
        if(firstname.compareTo(o.getFirstname()) == 0 &&
           surname.compareTo(o.getSurname()) == 0 &&
           email.compareTo(o.getEmail()) == 0)
            return 0;
        if(firstname.compareTo(o.getFirstname()) < 0)
            return -1;
        if(firstname.compareTo(o.getFirstname()) > 0)
            return 1;
        if(surname.compareTo(o.getSurname()) < 0)
            return -1;
        if(surname.compareTo(o.getSurname()) > 0)
            return 1;
        if(email.compareTo(o.getEmail()) < 0)
            return -1;
        if(email.compareTo(o.getEmail()) > 0)
            return 1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return firstname + " - " + surname + " - " + email;
    }
}
