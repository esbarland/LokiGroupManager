package com.example.lokigroupmanager.BasicsActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;

import com.example.lokigroupmanager.Adapters.EventAdapter;
import com.example.lokigroupmanager.Dialogs.EventInfoDialog;
import com.example.lokigroupmanager.Dialogs.UserInfoDialog;
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
                    Log.d("esbarland", "day event: " + event.getDate().getDate());



                    if (currentDate.getYear()-1900 == event.getDate().getYear() && currentDate.getMonth() == event.getDate().getMonth() && currentDate.getDate() == event.getDate().getDate()) {
                        listCurrentsEvents.add(event);
                    }
                }
                EventAdapter adapter = new EventAdapter(context, listCurrentsEvents);
                listViewEvents.setAdapter(adapter);


                listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle event = new Bundle();
                        event.putParcelable("event", listCurrentsEvents.get(position));
                        event.putInt("pos", position);

                        EventInfoDialog eventInfoDialog = new EventInfoDialog();
                        eventInfoDialog.setArguments(event);
                        eventInfoDialog.show(getSupportFragmentManager(), EventInfoDialog.TAG);
                    }
                });

            }
        });



    }
}
