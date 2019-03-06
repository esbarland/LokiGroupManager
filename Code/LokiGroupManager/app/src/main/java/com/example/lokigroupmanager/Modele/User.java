package com.example.lokigroupmanager.Modele;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Serializable, Parcelable {
    private static final long serialVersionUID = 571475165L;
    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private String firstname;
    private String surname;
    private String email;

    public User(String firstname, String surname, String email) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
    }

    public User(Parcel in) {
        String[] values = new String[3];
        in.readStringArray(values);
        this.firstname = values[0];
        this.surname = values[1];
        this.email = values[2];
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] values = new String[3];
        values[0] = firstname;
        values[1] = surname;
        values[2] = email;
        dest.writeStringArray(values);
    }

    @Override
    public String toString() {
        return firstname + " - " + surname + " - " + email;
    }
}
