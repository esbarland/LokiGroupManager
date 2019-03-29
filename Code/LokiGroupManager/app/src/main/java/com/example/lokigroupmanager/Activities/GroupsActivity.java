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

import com.example.lokigroupmanager.Adapters.GroupAdapter;
import com.example.lokigroupmanager.Dialogs.AddGroupDialog;
import com.example.lokigroupmanager.Dialogs.GroupInfoDialog;
import com.example.lokigroupmanager.HardwareEvents.ShakeEvent;
import com.example.lokigroupmanager.Model.Group;
import com.example.lokigroupmanager.Model.User;
import com.example.lokigroupmanager.Persistence.StubDataManager;
import com.example.lokigroupmanager.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupsActivity extends AppCompatActivity implements AddGroupDialog.AddGroupDialogListener, GroupInfoDialog.GroupDeleteDialogListener {

    private ListView listViewGroups;
    private List<Group> listGroups = new ArrayList<>();
    private GroupAdapter adapter;
    private ShakeEvent shakeEvent;

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

        if(savedInstanceState != null){
            // IF PHONES ORIENTATION CHANGED
            // RESTORE GROUP LIST FROM onSaveInstanceState BUNDLE
            listGroups = (ArrayList<Group>) savedInstanceState.getSerializable("groups");
        }
        else {
            // RESTORE GROUP LIST FROM INTERNAL STORAGE
            FileInputStream fis;
            ObjectInputStream ois;

            try {
                fis = openFileInput("groups");
                ois = new ObjectInputStream(fis);
                listGroups = (ArrayList<Group>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //StubDataManager sm = new StubDataManager();
        //listGroups = sm.loadGroups();

        // Array Adapter
        adapter = new GroupAdapter(this, listGroups);
        listViewGroups.setAdapter(adapter);

        // On item click listener of the user list
        listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle group = new Bundle();
                // Put the user selected in a bundle
                group.putSerializable("group", listGroups.get(position));
                group.putInt("pos", position);

                // Launch the info dialog with the bundle
                GroupInfoDialog groupInfoDialog = new GroupInfoDialog();
                groupInfoDialog.setArguments(group);
                groupInfoDialog.show(getSupportFragmentManager(), "group_info");
            }
        });

        // Start shake detection
        final Snackbar sorted = Snackbar.make(findViewById(android.R.id.content), R.string.group_sort, Snackbar.LENGTH_SHORT);
        shakeEvent = new ShakeEvent((SensorManager) getSystemService(Context.SENSOR_SERVICE));
        shakeEvent.setOnShakeListener(new ShakeEvent.OnShakeListener() {
            @Override
            public void onShake() {
                // Sort group list and notify the adapter
                Collections.sort(listGroups);
                adapter.notifyDataSetChanged();
                sorted.show();
            }
        });
    }

    /***
     * Save the group list in a temporary bundle when the phone orientation change
     * @param outState saved bundle that is transfer to onCreate method
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("groups", (ArrayList<Group>) listGroups);
        super.onSaveInstanceState(outState);
    }

    /***
     * Save the groups on stop of the activity
     */
    @Override
    protected void onStop() {
        FileOutputStream fileOutputStream;
        ObjectOutputStream outputStream;

        try {

            fileOutputStream = openFileOutput("groups", Context.MODE_PRIVATE);
            outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(listGroups);

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

    /***
     * Remove group bundle reception
     * @param bundle group to remove
     */
    @Override
    public void onDeleteDialog(Bundle bundle) {
        listGroups.remove(bundle.getInt("pos"));
        adapter.notifyDataSetChanged();
    }
}
