package com.example.lokigroupmanager.BasicsActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ListView;

import com.example.lokigroupmanager.Adapters.EventAdapter;
import com.example.lokigroupmanager.Modele.Event;
import com.example.lokigroupmanager.Persistence.StubDataManager;
import com.example.lokigroupmanager.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private ListView listViewEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        calendarView = (CalendarView) findViewById(R.id.calendar);
        listViewEvents = (ListView) findViewById(R.id.listEvents);

        StubDataManager stubDataManager = new StubDataManager();
        final List<Event> listEvents = stubDataManager.loadEvents();

        final Activity context = this;

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //when day change get the events of the day
                Date currentDate = new Date(year, month, dayOfMonth);

                //list of the events of the selected date
                final List<Event> listCurrentsEvents = new ArrayList<>();

                for (Event event : listEvents) {
                    if (event.getDate().equals(currentDate)) {
                        listCurrentsEvents.add(event);
                    }
                }
                EventAdapter adapter = new EventAdapter(context, listCurrentsEvents);
                listViewEvents.setAdapter(adapter);


            }
        });



    }
}
