package com.example.lokigroupmanager.Model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Group implements Serializable, Comparable<Group> {
    private static final long serialVersionUID = 5497837624L;

    private List<User> listUsers;
    private String groupName;
    private String description;

    public Group(List<User> listUsers, String groupName, String description){
        this.listUsers = listUsers;
        this.groupName = groupName;
        this.description = description;
    }

    public List<User> getListUsers() {
        //return Collections.unmodifiableList(listUsers);
        return listUsers;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addToList(User user){
        if(listUsers.contains(user))
            return;

        listUsers.add(user);
    }

    public void removeFromList(User user){
        if(!listUsers.contains(user))
            return;

        listUsers.remove(user);
    }

    @Override
    public int compareTo(@NonNull Group o) {
        if(groupName.compareTo(o.getGroupName()) == 0 && description.compareTo(o.getDescription()) == 0)
            return 0;
        else
            if(groupName.compareTo(o.getGroupName()) == 0)
                return description.compareTo(o.getDescription());
            else
                return groupName.compareTo(o.getGroupName());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Group " + groupName + "\n");

        for (User user : listUsers) {
            sb.append(user.getFirstname() + " - " + user.getSurname() + " - " + user.getEmail() + "\n");
        }

        return sb.toString();
    }
}
