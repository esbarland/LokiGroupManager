package com.example.lokigroupmanager.Activities;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lokigroupmanager.Adapters.UserAdapter;
import com.example.lokigroupmanager.Dialogs.AddUserDialog;
import com.example.lokigroupmanager.Dialogs.UserInfoDialog;
import com.example.lokigroupmanager.HardwareEvents.ShakeEvent;
import com.example.lokigroupmanager.Model.User;
import com.example.lokigroupmanager.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements AddUserDialog.AddUserDialogListener, UserInfoDialog.UserDeleteDialogListener {

    private ListView listViewUsers;
    private List<User> listAllUsers = new ArrayList<>();
    private UserAdapter adapter;
    private ShakeEvent shakeEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        // FLOATING ACTION BUTTON : ADD A USER
        FloatingActionButton fab = findViewById(R.id.add_user_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUserDialog dialog = new AddUserDialog();
                dialog.show(getSupportFragmentManager(), AddUserDialog.TAG);
            }
        });

        listViewUsers = findViewById(R.id.listUsers);

        if(savedInstanceState != null){
            // IF PHONES ORIENTATION CHANGED
            listAllUsers = (ArrayList<User>) savedInstanceState.getSerializable("users");
        }
        else {
            // RESTORE USERS LIST FROM INTERNAL STORAGE
            FileInputStream fis;
            ObjectInputStream ois;

            try {
                fis = openFileInput("users");
                ois = new ObjectInputStream(fis);
                listAllUsers = (ArrayList<User>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Personal ListView Adapter for Users (see: UserAdapter class)
        adapter = new UserAdapter(this, listAllUsers);
        listViewUsers.setAdapter(adapter);

        // On item click listener of the user list
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle user = new Bundle();
                // Put the user selected in a bundle
                user.putSerializable("user", listAllUsers.get(position));
                user.putInt("pos", position);

                // Launch the info dialog with the bundle
                UserInfoDialog userInfoDialog = new UserInfoDialog();
                userInfoDialog.setArguments(user);
                userInfoDialog.show(getSupportFragmentManager(), "user_info");
            }
        });

        // Start shake detection
        final Snackbar sorted = Snackbar.make(findViewById(android.R.id.content), R.string.user_sort, Snackbar.LENGTH_SHORT);
        shakeEvent = new ShakeEvent((SensorManager) getSystemService(Context.SENSOR_SERVICE));
        shakeEvent.setOnShakeListener(new ShakeEvent.OnShakeListener() {
            @Override
            public void onShake() {
                // Sort user list and notify the adapter
                Collections.sort(listAllUsers);
                adapter.notifyDataSetChanged();
                sorted.show();
            }
        });
    }

    /***
     * Save the user list when the phone orientation change
     * @param outState saved bundle
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("users", (ArrayList<User>) listAllUsers);
        super.onSaveInstanceState(outState);
    }

    /***
     * Save the users on stop
     */
    @Override
    protected void onStop() {
        FileOutputStream fileOutputStream;
        ObjectOutputStream outputStream;

        try {

            fileOutputStream = openFileOutput("users", Context.MODE_PRIVATE);
            outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(listAllUsers);

            outputStream.close();
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Stop shake detection
        shakeEvent.removeOnShakeListener();
        super.onStop();
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

    /***
     * Delete user bundle reception
     * @param bundle UserInfoDialog bundle
     *               user -> User to delete, pos -> position in the ArrayList
     */
    @Override
    public void onDeleteDialog(Bundle bundle) {
        // Remove the user and notify the adapter
        listAllUsers.remove(bundle.getInt("pos"));
        adapter.notifyDataSetChanged();
    }


}
