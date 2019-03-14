package com.example.lokigroupmanager.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lokigroupmanager.Modele.Event;
import com.example.lokigroupmanager.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends ArrayAdapter<Event> {
    private final Activity context;
    private final List<Event> listEvents;

    public EventAdapter(Activity context, List<Event> listEvents) {
        super(context, R.layout.event_list_item, listEvents);
        this.context = context;
        this.listEvents = listEvents;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        // RISQUE DE CRASH
        View view = inflater.inflate(R.layout.event_list_item, null, false);

        TextView nameTextView = view.findViewById(R.id.nameEventView);
        TextView placeTextView = view.findViewById(R.id.placeEventView);
        TextView datelTextView = view.findViewById(R.id.dateEventView);

        Event currentEvent = listEvents.get(position);

        nameTextView.setText(currentEvent.getNameEvent());

        //Formattage de la date mais langue en dur
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE);

        String resultDate = formatter.format(currentEvent.getDate());
        datelTextView.setText(resultDate);

        return view;
    }
}
