package com.example.lokigroupmanager.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;

import com.example.lokigroupmanager.Adapters.EventAdapter;
import com.example.lokigroupmanager.Dialogs.AddEventDialog;
import com.example.lokigroupmanager.Dialogs.EventInfoDialog;
import com.example.lokigroupmanager.Model.Event;
import com.example.lokigroupmanager.Persistence.StubDataManager;
import com.example.lokigroupmanager.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity implements EventInfoDialog.EventDeleteDialogListener, AddEventDialog.AddEventDialogListener {

    private ListView listViewEvents;
    private EventAdapter adapter;
    private List<Event> listCurrentsEvents = new ArrayList<>();
    ;
    private List<Event> listEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        FloatingActionButton fab = findViewById(R.id.add_event_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEventDialog dialog = new AddEventDialog();
                dialog.show(getSupportFragmentManager(), AddEventDialog.TAG);
            }
        });

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar);
        listViewEvents = (ListView) findViewById(R.id.listEvents);

        StubDataManager stubDataManager = new StubDataManager();
        listEvents = stubDataManager.loadEvents();

        final Activity context = this;

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //when day change get the events of the day
                Date currentDate = new Date(year, month, dayOfMonth);


                //list of the events of the selected date
                listCurrentsEvents.clear();

                for (Event event : listEvents) {
                    Log.d("esbarland", "############################");
                    Log.d("esbarland", "event name: " + event.getNameEvent());
                    if (currentDate.getYear() - 1900 == event.getDate().getYear() && currentDate.getMonth() == event.getDate().getMonth() && currentDate.getDate() == event.getDate().getDate()) {
                        listCurrentsEvents.add(event);
                    }
                }
                adapter = new EventAdapter(context, listCurrentsEvents);
                listViewEvents.setAdapter(adapter);


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

    @Override
    public void onDeleteDialog(Bundle bundle) {
        // Remove the event from 2 lists and notify the adapter
        Event event = listCurrentsEvents.get(bundle.getInt("pos"));

        listCurrentsEvents.remove(event);
        listEvents.remove(event);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFinishAddDialog(Bundle bundle) {
        if (bundle != null) {
            Event event = (Event) bundle.getSerializable("eventAdded");
            listEvents.add(event);

            //adapter.notifyDataSetChanged();

        }
    }
}
