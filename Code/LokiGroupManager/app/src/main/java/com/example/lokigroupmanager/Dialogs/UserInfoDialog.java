package com.example.lokigroupmanager.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.lokigroupmanager.Model.User;
import com.example.lokigroupmanager.R;

public class UserInfoDialog extends DialogFragment {
    /***
     * User to display
     */
    private User user;

    // Interface to send and receive data from the dialog to the activity
    public interface UserDeleteDialogListener {
        void onDeleteDialog(Bundle bundle);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the user selected
        user = (User) getArguments().getSerializable("user");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.user_dialog, null))
                // Add action button
                .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserDeleteDialogListener listener = (UserDeleteDialogListener) getActivity();
                        listener.onDeleteDialog(getArguments());
                        dismiss();
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
            TextView firstname = dialog.findViewById(R.id.firstnameViewDialog);
            TextView surname = dialog.findViewById(R.id.surnameViewDialog);
            TextView email = dialog.findViewById(R.id.emailViewDialog);

            // Set the TextViews content by user's content
            firstname.setText(user.getFirstname());
            surname.setText(user.getSurname());
            email.setText(user.getEmail());
        }
    }
}
