package com.example.lokigroupmanager.Persistence;

import com.example.lokigroupmanager.Model.Event;

import java.util.List;

public interface ILoaderEvent {

    List<Event> loadEvents();

}
