package com.example.lokigroupmanager.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lokigroupmanager.Adapters.UserAdapter;
import com.example.lokigroupmanager.Model.Group;
import com.example.lokigroupmanager.Model.User;
import com.example.lokigroupmanager.R;

import java.util.ArrayList;

public class GroupInfoDialog extends DialogFragment {
    // Group to display
    private Group group;

    // Interface to send and receive data from the dialog to the activity
    public interface GroupDeleteDialogListener {
        void onDeleteDialog(Bundle bundle);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the user selected
        group = (Group) getArguments().getSerializable("group");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.group_dialog, null))
                // Add action button
                .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GroupDeleteDialogListener listener = (GroupDeleteDialogListener) getActivity();
                        listener.onDeleteDialog(getArguments());
                        dismiss();
                    }
                })
                .setNeutralButton(R.string.modify_users, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Launch the user list tick boxes dialog
                        Bundle b = new Bundle();
                        b.putSerializable("groupUsers", (ArrayList<User>) group.getListUsers());
                        EditGroupDialog editDialog = new EditGroupDialog();
                        editDialog.setArguments(b);
                        editDialog.show(getFragmentManager(), EditGroupDialog.TAG);
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        final Dialog dialog = getDialog();
        if(dialog != null) {
            // Get TextView Components of the dialog
            TextView groupname = dialog.findViewById(R.id.groupNameViewDialog);
            TextView description = dialog.findViewById(R.id.descriptionViewDialog);
            ListView listView = dialog.findViewById(R.id.groupUsersViewDialog);

            // Set the TextViews content by user's content
            groupname.setText(group.getGroupName());
            description.setText(group.getDescription());

            UserAdapter adapter = new UserAdapter(getActivity(), group.getListUsers());
            listView.setAdapter(adapter);
        }
    }

}
