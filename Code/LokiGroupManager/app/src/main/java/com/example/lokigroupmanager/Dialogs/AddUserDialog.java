package com.example.lokigroupmanager.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.lokigroupmanager.BasicsActivity.UsersActivity;
import com.example.lokigroupmanager.R;

public class AddUserDialog extends DialogFragment {
    public static String TAG = "FullScreenDialog";

    // Interface to send and receive data from the dialog to the activity
    public interface AddUserDialogListener {
        void onFinishAddDialog(Bundle bundle);
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
        final View view = inflater.inflate(R.layout.add_user_dialog, container, false);
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

            // Get the Button components from the view
            ImageButton close = getView().findViewById(R.id.add_user_close);
            Button save = getView().findViewById(R.id.add_user_save);

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
                    // Get the EditText components from the view.
                    EditText firstnameEditText = getView().findViewById(R.id.add_user_firstname);
                    EditText surnameEditText = getView().findViewById(R.id.add_user_surname);
                    EditText emailEditText = getView().findViewById(R.id.add_user_email);

                    // Get the texts from the EditText components
                    String firstname = firstnameEditText.getText().toString();
                    String surname = surnameEditText.getText().toString();
                    String email = emailEditText.getText().toString();

                    // Values check
                    if(firstname.equals("") || surname.equals("") || email.equals("")) {
                        // Error case
                        Snackbar.make(getView(), getString(R.string.emptyfield), Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        // Send the bundle to the activity
                        Bundle infos = new Bundle();
                        infos.putString("firstname", firstname);
                        infos.putString("surname", surname);
                        infos.putString("email", email);
                        AddUserDialogListener listener = (AddUserDialogListener) getActivity();
                        listener.onFinishAddDialog(infos);
                        dismiss();
                    }
                }
            });
        }
    }
}
