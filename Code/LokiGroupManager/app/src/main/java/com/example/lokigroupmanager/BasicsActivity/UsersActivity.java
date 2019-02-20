package com.example.lokigroupmanager.BasicsActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lokigroupmanager.Modele.Group;
import com.example.lokigroupmanager.Modele.User;
import com.example.lokigroupmanager.Persistence.StubDataManager;
import com.example.lokigroupmanager.R;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity{

    private ListView listViewUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        listViewUsers = (ListView) findViewById(R.id.listUsers);

        StubDataManager stub = new StubDataManager();

        List<Group> listGroups = stub.loadGroups();

        List<User> listAllUsers = new ArrayList<>();

        for (Group group : listGroups) {
            listAllUsers.addAll(group.getListUsers());
        }

        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listAllUsers);

        listViewUsers.setAdapter(adapter);

    }



}
