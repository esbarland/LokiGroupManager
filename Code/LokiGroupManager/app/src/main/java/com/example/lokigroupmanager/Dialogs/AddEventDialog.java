package com.example.lokigroupmanager.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.lokigroupmanager.Model.Event;
import com.example.lokigroupmanager.R;

import java.util.Date;

public class AddEventDialog extends DialogFragment {
    public static String TAG = "ADD_EVENT_DIALOG";

    // Interface to send and receive data from the dialog to the activity
    public interface AddEventDialogListener {
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
        final View view = inflater.inflate(R.layout.add_event_dialog, container, false);
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

            ImageButton close = (ImageButton) getView().findViewById(R.id.add_event_close);
            Button save = (Button) getView().findViewById(R.id.add_event_save);

            // Close button action
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            // Save button action
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText nameEditText = getView().findViewById(R.id.add_event_nameEvent);
                    EditText placeEditText = getView().findViewById(R.id.add_event_placeEvent);
                    DatePicker datePicker = getView().findViewById(R.id.add_event_dateEvent);
                    EditText descriptionEditText = getView().findViewById(R.id.add_event_descriptionEvent);

                    String name = nameEditText.getText().toString();
                    String place = placeEditText.getText().toString();
                    Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                    String description = descriptionEditText.getText().toString();

                    /*
                     * Crash à ce niveau passer les paramètres un par un au lieu de créer un event et de le passer
                     */
                    if (name.equals("") || place.equals("") || description.equals("")) {
                        Snackbar.make(getView(), getString(R.string.emptyfield), Snackbar.LENGTH_LONG).show();
                    } else {
                        Bundle infos = new Bundle();
                        Event event = new Event(name, description, place, date);
                        infos.putSerializable("eventAdded", event);

                        AddEventDialogListener listener = (AddEventDialogListener) getActivity();
                        assert listener != null;
                        listener.onFinishAddDialog(infos);
                        dismiss();
                    }


                }
            });


        }


    }
}
