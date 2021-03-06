package com.example.lokigroupmanager.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.lokigroupmanager.Adapters.EditGroupAdapter;
import com.example.lokigroupmanager.Model.User;
import com.example.lokigroupmanager.R;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class EditGroupDialog extends DialogFragment {
    public static String TAG = "FullScreenDialog";
    private List<User> allUsers = new ArrayList<>();
    private List<User> usersSelected = new ArrayList<>();
    private ListView list;
    private EditGroupAdapter adapter;

    // Interface to send and receive data from the dialog to the activity
    public interface EditGroupDialogListener {
        void onFinishEditDialog(Bundle bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.edit_group_dialog, container, false);

        // Get the Button components from the view
        ImageButton close = view.findViewById(R.id.edit_group_close);
        Button save = view.findViewById(R.id.edit_group_save);
        list = view.findViewById(R.id.editGroupListView);
        adapter = new EditGroupAdapter(getActivity(), allUsers);
        list.setAdapter(adapter);

        // Marche pas :'(
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                User user = allUsers.get(position);
                if(user.selected()){
                    user.setSelected(false);
                    Log.w("check", user+": false");
                }
                else{
                    user.setSelected(true);
                    Log.w("check", user+": true");
                }

                allUsers.set(position, user);
                adapter.updateAdapter(allUsers);
            }
        });

        // Close button behavior
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        // Save button behavior
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);

            FileInputStream fis;
            ObjectInputStream ois;
            try {
                fis = getActivity().openFileInput("users");
                ois = new ObjectInputStream(fis);
                allUsers = (ArrayList<User>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            usersSelected = (ArrayList<User>) getArguments().getSerializable("groupUsers");
            adapter.notifyDataSetChanged();
        }
    }

    private void updateCheckBoxes(EditGroupAdapter adapter) {
        for (int i = 0; i<allUsers.size(); i++) {
            View item = adapter.getView(i, null, null);
            if(usersSelected.contains(allUsers.get(i))){
                CheckBox cb = item.findViewById(R.id.checkBoxSelection);
                cb.setChecked(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void getCheckedUsers(){

    }
}
