package com.example.lokigroupmanager.BasicsActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lokigroupmanager.Adapters.GroupAdapter;
import com.example.lokigroupmanager.Dialogs.AddGroupDialog;
import com.example.lokigroupmanager.Modele.Group;
import com.example.lokigroupmanager.Modele.User;
import com.example.lokigroupmanager.Persistence.StubDataManager;
import com.example.lokigroupmanager.R;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends AppCompatActivity implements AddGroupDialog.AddGroupDialogListener {

    private ListView listViewGroups;
    private List<Group> listGroups;
    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        // Add Group Dialog Floating Action Button
        FloatingActionButton fab = findViewById(R.id.add_group_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddGroupDialog dialog = new AddGroupDialog();
                dialog.show(getSupportFragmentManager(), AddGroupDialog.TAG);
            }
        });

        listViewGroups = findViewById(R.id.listGroups);

        // Stub
        StubDataManager stub = new StubDataManager();
        listGroups = stub.loadGroups();

        // Array Adapter
        adapter = new GroupAdapter(this, listGroups);
        listViewGroups.setAdapter(adapter);
    }

    /***
     * Add group bundle reception
     * @param bundle group's informations to add
     *               name and description as keys.
     */
    @Override
    public void onFinishAddDialog(Bundle bundle) {
        if(bundle != null){
            Group g = new Group(
                    new ArrayList<User>(),
                    bundle.getString("name"),
                    bundle.getString("description"));
            listGroups.add(g);
            adapter.notifyDataSetChanged();
        }
    }
}
