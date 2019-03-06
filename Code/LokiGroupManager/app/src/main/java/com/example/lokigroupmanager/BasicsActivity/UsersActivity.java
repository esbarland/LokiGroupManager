package com.example.lokigroupmanager.BasicsActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.lokigroupmanager.Adapters.UserAdapter;
import com.example.lokigroupmanager.Dialogs.AddUserDialog;
import com.example.lokigroupmanager.Modele.Group;
import com.example.lokigroupmanager.Modele.User;
import com.example.lokigroupmanager.Persistence.StubDataManager;
import com.example.lokigroupmanager.R;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements AddUserDialog.AddUserDialogListener {

    private ListView listViewUsers;
    private ArrayList<User> listAllUsers;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_users);

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUserDialog dialog = new AddUserDialog();
                dialog.show(getSupportFragmentManager(), AddUserDialog.TAG);
            }
        });

        listViewUsers = findViewById(R.id.listUsers);

        if(savedInstanceState != null){
            listAllUsers = savedInstanceState.getParcelableArrayList("users");
        }
        else {
            StubDataManager stub = new StubDataManager();

            List<Group> listGroups = stub.loadGroups();

            listAllUsers = new ArrayList<>();

            for (Group group : listGroups) {
                listAllUsers.addAll(group.getListUsers());
            }
        }

        // Personal ListView Adapter for Users (see: UserAdapter class)
        adapter = new UserAdapter(this, listAllUsers);
        listViewUsers.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("users", listAllUsers);
        super.onSaveInstanceState(outState);
    }

    /***
     * Add user bundle reception
     * @param bundle user's informations to add
     *               firstname, surname, email as keys.
     */
    @Override
    public void onFinishAddDialog(Bundle bundle) {
        if(bundle != null){
            User u = new User(
                    bundle.getString("firstname"),
                    bundle.getString("surname"),
                    bundle.getString("email"));
            listAllUsers.add(u);
            adapter.notifyDataSetChanged();
        }
    }
}
