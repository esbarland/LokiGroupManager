package com.example.lokigroupmanager.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;

import com.example.lokigroupmanager.Adapters.EventAdapter;
import com.example.lokigroupmanager.Dialogs.AddEventDialog;
import com.example.lokigroupmanager.Dialogs.EventInfoDialog;
import com.example.lokigroupmanager.Model.Event;
import com.example.lokigroupmanager.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity implements EventInfoDialog.EventDeleteDialogListener, AddEventDialog.AddEventDialogListener {

    private ListView listViewEvents;
    private EventAdapter adapter;
    private List<Event> listCurrentsEvents = new ArrayList<>();
    private List<Event> listEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Floating button to add event
        FloatingActionButton fab = findViewById(R.id.add_event_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEventDialog dialog = new AddEventDialog();
                dialog.show(getSupportFragmentManager(), AddEventDialog.TAG);
            }
        });


        if (savedInstanceState != null) {
            //restore list events if phone orientation change
            listEvents = (ArrayList<Event>) savedInstanceState.getSerializable("events");
        } else {
            //get list of events from the phone storage
            FileInputStream fileInputStream;
            ObjectInputStream objectInputStream;

            try {
                fileInputStream = openFileInput("events");
                objectInputStream = new ObjectInputStream(fileInputStream);
                listEvents = (ArrayList<Event>) objectInputStream.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar);
        listViewEvents = (ListView) findViewById(R.id.listEvents);

        final Activity context = this;

        //Event handle when click on a date on the calendar
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Date currentDate = new Date(year, month, dayOfMonth);

                //if the list of event is null return
                if (listEvents == null)
                    return;

                //list of the events of the selected date and clear it
                listCurrentsEvents.clear();

                //add all event from the selected date
                for (Event event : listEvents) {
                    if (currentDate.getYear() - 1900 == event.getDate().getYear() && currentDate.getMonth() == event.getDate().getMonth() && currentDate.getDate() == event.getDate().getDate()) {
                        listCurrentsEvents.add(event);
                    }
                }
                adapter = new EventAdapter(context, listCurrentsEvents);
                listViewEvents.setAdapter(adapter);


                //more info when click on a specific date and can delete it
                listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle event = new Bundle();
                        event.putSerializable("event", listCurrentsEvents.get(position));
                        event.putInt("pos", position);

                        EventInfoDialog eventInfoDialog = new EventInfoDialog();
                        eventInfoDialog.setArguments(event);
                        eventInfoDialog.show(getSupportFragmentManager(), EventInfoDialog.TAG);
                    }
                });

            }
        });


    }

    /**
     * Save the events list when the orientation of the phone change
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("events", (ArrayList<Event>) listEvents);
        super.onSaveInstanceState(outState);
    }

    /**
     * Save the list in the phone storage when the app stop
     */
    @Override
    protected void onStop() {
        FileOutputStream fileOutputStream;
        ObjectOutputStream outputStream;

        try {
            fileOutputStream = openFileOutput("events", Context.MODE_PRIVATE);
            outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(listEvents);

            outputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onStop();
    }

    /**
     * Delete event from the two lists
     *
     * @param bundle
     */
    @Override
    public void onDeleteDialog(Bundle bundle) {
        // Remove the event from 2 lists and notify the adapter
        Event event = listCurrentsEvents.get(bundle.getInt("pos"));

        listCurrentsEvents.remove(event);
        listEvents.remove(event);

        adapter.notifyDataSetChanged();
    }

    /**
     * Add event to the list of events
     *
     * @param bundle
     */
    @Override
    public void onFinishAddDialog(Bundle bundle) {
        if (bundle != null) {
            Event event = (Event) bundle.getSerializable("eventAdded");
            listEvents.add(event);

        }
    }
}
