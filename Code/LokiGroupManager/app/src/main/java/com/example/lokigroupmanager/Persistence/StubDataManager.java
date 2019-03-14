package com.example.lokigroupmanager.Persistence;

import com.example.lokigroupmanager.Modele.Event;
import com.example.lokigroupmanager.Modele.Group;
import com.example.lokigroupmanager.Modele.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class StubDataManager implements ILoaderGroup, ILoaderEvent {

    @Override
    public List<Group> loadGroups() {
        List<Group> listGroups = new ArrayList<>();

        List<User> listUser1 = new ArrayList<>();
        List<User> listUser2 = new ArrayList<>();
        List<User> listUser3 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String name = getSaltString();
            String reversedName = new StringBuilder(name).reverse().toString();
            String mail = name + "." + reversedName + "@etu.uca.fr";

            User user = new User(name, reversedName, mail);
            listUser1.add(user);
        }

        for (int i = 0; i < 4; i++) {
            String name = getSaltString();
            String reversedName = new StringBuilder(name).reverse().toString();
            String mail = name + "." + reversedName + "@etu.uca.fr";

            User user = new User(name, reversedName, mail);
            listUser2.add(user);
        }

        for (int i = 0; i < 13; i++) {
            String name = getSaltString();
            String reversedName = new StringBuilder(name).reverse().toString();
            String mail = name + "." + reversedName + "@etu.uca.fr";

            User user = new User(name, reversedName, mail);
            listUser3.add(user);
        }

        Group groupA = new Group(listUser1, "A", "testA");
        Group groupB = new Group(listUser2, "B", "testB");
        Group groupC = new Group(listUser3, "C", "testC");

        listGroups.add(groupA);
        listGroups.add(groupB);
        listGroups.add(groupC);

        return listGroups;
    }

    private String getSaltString() {
        String SALTCHARS = "abcdefghijklmnopqrstuvxwyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 7) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    @Override
    public List<Event> loadEvents() {
        final List<Event> listEvents = new ArrayList<>();

        // 119 = 2019
        // pour mettre un mois faire -1 par rapport au calendrier normal exemple : mars = 02 et pas 03
        listEvents.add(new Event("gouter", "manger des gateaux", "maison", new Date(119, 2, 8)));
        listEvents.add(new Event("cinema", "regarder un film", "cinéma", new Date(119, 2, 30)));
        listEvents.add(new Event("bk", "manger fast food", "bk", new Date(119, 2, 8)));

        return listEvents;
    }
}
