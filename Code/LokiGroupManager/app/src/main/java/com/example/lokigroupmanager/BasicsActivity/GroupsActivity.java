package com.example.lokigroupmanager.BasicsActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lokigroupmanager.Adapters.GroupAdapter;
import com.example.lokigroupmanager.Modele.Group;
import com.example.lokigroupmanager.Persistence.StubDataManager;
import com.example.lokigroupmanager.R;

import java.util.List;

public class GroupsActivity extends AppCompatActivity {

    private ListView listViewGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);


        /*
        * Test d'afficahge des groupes via toString construire un layout custom pour la liste views
        */
        listViewGroups = (ListView) findViewById(R.id.listGroups);

        StubDataManager stub = new StubDataManager();

        List<Group> listGroups = stub.loadGroups();

        //ArrayAdapter<Group> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listGroups);
        GroupAdapter adapter = new GroupAdapter(this, listGroups);

        listViewGroups.setAdapter(adapter);




    }
}
