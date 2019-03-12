package com.example.lokigroupmanager.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.lokigroupmanager.R;

public class AddGroupDialog extends DialogFragment {
    public static String TAG = "FullScreenDialog";

    public interface AddGroupDialogListener {
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
        final View view = inflater.inflate(R.layout.add_group_dialog, container, false);
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
            ImageButton close = getView().findViewById(R.id.add_group_close);
            Button save = getView().findViewById(R.id.add_group_save);

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
                    EditText nameEditText = getView().findViewById(R.id.add_group_name);
                    EditText descriptionEditText = getView().findViewById(R.id.add_group_description);

                    // Get the texts from the EditText components
                    String name = nameEditText.getText().toString();
                    String description = descriptionEditText.getText().toString();

                    // Values check
                    if(name.equals("") || description.equals("")) {
                        // Error case
                        Snackbar.make(getView(), getString(R.string.emptyfield), Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        // Send the bundle to the activity
                        Bundle infos = new Bundle();
                        infos.putString("name", name);
                        infos.putString("description", description);
                        AddGroupDialogListener listener = (AddGroupDialogListener) getActivity();
                        listener.onFinishAddDialog(infos);
                        dismiss();
                    }
                }
            });
        }
    }
}
