package com.example.lokigroupmanager.Persistence;

import com.example.lokigroupmanager.Model.Group;

import java.util.List;

public interface ISaverGroup {

    void saveGroups(List<Group> listGroups);

}
