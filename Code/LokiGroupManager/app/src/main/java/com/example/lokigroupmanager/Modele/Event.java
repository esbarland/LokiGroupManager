package com.example.lokigroupmanager.Modele;

import java.util.Date;

public class Event {

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


}