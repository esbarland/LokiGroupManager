package com.example.lokigroupmanager.BasicsActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lokigroupmanager.Adapters.UserAdapter;
import com.example.lokigroupmanager.Dialogs.AddUserDialog;
import com.example.lokigroupmanager.Dialogs.UserInfoDialog;
import com.example.lokigroupmanager.HardwareEvent.ShakeEvent;
import com.example.lokigroupmanager.Modele.User;
import com.example.lokigroupmanager.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity implements AddUserDialog.AddUserDialogListener, UserInfoDialog.UserDeleteDialogListener {

    private ListView listViewUsers;
    private ArrayList<User> listAllUsers = new ArrayList<>();
    private UserAdapter adapter;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeEvent shakeEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

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
            listAllUsers = savedInstanceState.getParcelableArrayList("users");
        }
        else {
            /* STUB
            StubDataManager stub = new StubDataManager();

            List<Group> listGroups = stub.loadGroups();

            listAllUsers = new ArrayList<>();

            for (Group group : listGroups) {
                listAllUsers.addAll(group.getListUsers());
            }

            */

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

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle user = new Bundle();
                user.putParcelable("user", listAllUsers.get(position));
                user.putInt("pos", position);

                UserInfoDialog userInfoDialog = new UserInfoDialog();
                userInfoDialog.setArguments(user);
                userInfoDialog.show(getSupportFragmentManager(), "user_info");
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeEvent = new ShakeEvent();
        shakeEvent.setOnShakeListener(new ShakeEvent.OnShakeListener() {

            @Override
            public void onShake(int count) {
                Log.w("SHAKE", "shake event");
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("users", listAllUsers);
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
