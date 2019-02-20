package com.example.lokigroupmanager.Modele;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Group implements Serializable {
    private static final long serialVersionUID = 5497837624L;

    private List<User> listUsers;
    private String groupName;

    public Group(List<User> listUsers, String groupName){
        this.listUsers = listUsers;
        this.groupName = groupName;
    }

    public Collection<User> getListUsers() {
        return Collections.unmodifiableList(listUsers);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Group " + groupName + "\n");

        for (User user : listUsers) {
            sb.append(user.getFirstname() + " - " + user.getSurname() + " - " + user.getEmail() + "\n");
        }

        return sb.toString();
    }
}
