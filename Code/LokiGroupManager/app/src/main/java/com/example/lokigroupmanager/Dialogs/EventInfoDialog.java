package com.example.lokigroupmanager.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.lokigroupmanager.Model.Event;
import com.example.lokigroupmanager.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class EventInfoDialog extends DialogFragment {
    //unique tag dialog
    public final static String TAG = "EVENT_INFO_DIALOG";

    // event to display
    private Event event;

    // Interface to send and receive data from the dialog to the activity
    public interface EventDeleteDialogListener {
        void onDeleteDialog(Bundle bundle);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the user selected
        event = (Event) getArguments().getSerializable("event");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.event_dialog, null))
                // Add action button
                .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EventInfoDialog.EventDeleteDialogListener listener = (EventInfoDialog.EventDeleteDialogListener) getActivity();
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
        if (dialog != null) {
            // Get TextView Components of the dialog
            TextView nameEvent = dialog.findViewById(R.id.eventNameViewDialog);
            TextView placeEvent = dialog.findViewById(R.id.eventPlaceViewDialog);
            TextView dateEvent = dialog.findViewById(R.id.eventDateViewDialog);
            TextView descriptionEvent = dialog.findViewById(R.id.eventDescriptionViewDialog);

            // Set the TextViews content by event's content
            nameEvent.setText(event.getNameEvent());
            placeEvent.setText(event.getPlace());

            //Date format
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE);
            String resultDate = formatter.format(event.getDate());

            dateEvent.setText(resultDate);
            descriptionEvent.setText(event.getDescription());
        }
    }
}
