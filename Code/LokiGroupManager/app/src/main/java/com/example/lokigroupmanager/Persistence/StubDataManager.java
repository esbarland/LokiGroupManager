package com.example.lokigroupmanager.Persistence;

import com.example.lokigroupmanager.Modele.Group;
import com.example.lokigroupmanager.Modele.User;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StubDataManager implements ILoader {

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

        for (int i = 0; i < 8; i++) {
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

        Group groupA = new Group(listUser1, "A");
        Group groupB = new Group(listUser2, "B");
        Group groupC = new Group(listUser3, "C");

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
}