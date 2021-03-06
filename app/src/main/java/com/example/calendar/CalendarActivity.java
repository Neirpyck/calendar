package com.example.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.calendar.adapter.EventAdapter;
import com.example.calendar.models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.graphics.Color.rgb;


public class CalendarActivity extends AppCompatActivity {


    final ArrayList<Event> eventsList = new ArrayList<>();
    // Get the database instance and store into object
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference events = database.getReference("events");

    //
    CalendarView simpleCalendarView;
    Date selectedDate = new Date(System.currentTimeMillis());
    SimpleDateFormat BDD_FORMAT = new SimpleDateFormat("dd-MM-y");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //Remplir Listview init
        ListView eventListView = findViewById(R.id.eventListView);
        EventAdapter eventAdapter = new EventAdapter(this, eventsList);
        eventListView.setAdapter(eventAdapter);

        for (int i = 0 ; i < eventsList.size() ; i++) {
            Event event = eventsList.get(i);
            if (event.get_isRappel()) {
                Event currentEvent = eventAdapter.getItem(i);
                Log.d("click", event.get_title());
                /*currentEvent.setOnClickListener(v -> {
                    View popUpView = inflater.inflate(R.layout.popupwindow, null);
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true;
                    final PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    popUpView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });
                });*/
            } else {
            }
        }


        events.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                SimpleDateFormat BASIC_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot child : children) {
                    Event event = child.getValue(Event.class);
                    try {
                        Date eventDate = BASIC_FORMAT.parse(event.get_date());
                        if (BDD_FORMAT.format(selectedDate).equals(BDD_FORMAT.format(eventDate))) {
                            eventsList.add(event);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                ((BaseAdapter) eventListView.getAdapter()).notifyDataSetChanged();
            }
        });


        //AppBar top screen
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Pick the date
        simpleCalendarView = findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        simpleCalendarView.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
        simpleCalendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
        simpleCalendarView.setSelectedWeekBackgroundColor(Color.RED); // red color for the selected week's background
        simpleCalendarView.setWeekSeparatorLineColor(Color.GREEN); // green color for the week separator line


        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = new Date(year - 1900, month, dayOfMonth);

                events.get().addOnCompleteListener(task -> {
                    task.getResult().getChildren();
                });

                events.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SimpleDateFormat BASIC_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                        Iterable<DataSnapshot> children = snapshot.getChildren();
                        eventsList.clear();
                        for (DataSnapshot child : children) {
                            Event event = child.getValue(Event.class);
                            try {
                                Date eventDate = BASIC_FORMAT.parse(event.get_date());
                                if (BDD_FORMAT.format(selectedDate).equals(BDD_FORMAT.format(eventDate))) {
                                    eventsList.add(event);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                        ((BaseAdapter) eventListView.getAdapter()).notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }

        });


        //FAB to create event
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            //Pass date to next screen
            Intent intent = new Intent(view.getContext(), AddEventActivity.class);
            intent.putExtra("date", selectedDate.getTime());
            startActivity(intent);
        });


    }


/*    public void onReminderClick(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.popupwindow, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popUpView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }*/



}

