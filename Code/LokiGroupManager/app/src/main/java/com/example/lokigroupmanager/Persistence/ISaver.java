package com.example.lokigroupmanager.Persistence;

import com.example.lokigroupmanager.Modele.Group;

import java.util.List;

public interface ISaver {

    void saveGroups(List<Group> listGroups);

}
