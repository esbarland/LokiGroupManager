package com.example.lokigroupmanager.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable, Parcelable {
    private static final long serialVersionUID = 372197271484L;
    public static final Parcelable.Creator<Event> CREATOR
            = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    private String nameEvent;
    private String description;
    private String place;
    private Date date;


    public Event(String nameEvent, String description, String place, Date date){
        this.nameEvent = nameEvent;
        this.description = description;
        this.place = place;
        this.date = date;
    }

    public Event(Parcel in) {
        String[] values = new String[3];
        in.readStringArray(values);
        this.nameEvent = values[0];
        this.description = values[1];
        this.place = values[2];
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public Date getDate() {
        return date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] values = new String[3];
        values[0] = nameEvent;
        values[1] = description;
        values[2] = place;
        dest.writeStringArray(values);
    }
}
