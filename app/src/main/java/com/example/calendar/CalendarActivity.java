  CalendarView simpleCalendarView;
    Date selectedDate =package com.example.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.calendar.adapter.EventAdapter;
import com.example.calendar.models.Event;
import com.example.calendar.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;



    public class CalendarActivity extends AppCompatActivity {




        // Get the database instance and store into object
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference events = database.getReference("events");
        DatabaseReference dates = database.getReference("dates");

   new Date(System.currentTimeMillis());

    SimpleDateFormat BDD_FORMAT = new SimpleDateFormat("dd-MM-Y");

    final ArrayList<Event> eventsList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        /*
        events.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                SimpleDateFormat BASIC_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Iterable<DataSnapshot> children = snapshot.getChildren();
                //Log.d("firebase", "Selected date: " + BDD_FORMAT.format(selectedDate));

                for (DataSnapshot child : children) {
                    Event event = child.getValue(Event.class);
                    //Log.d("firebase", event.get_title());
                    try {
                        Date eventDate = BASIC_FORMAT.parse(event.get_date());
                        //Log.d("firebase", BDD_FORMAT.format(eventDate));
                        if(BDD_FORMAT.format(selectedDate).equals(BDD_FORMAT.format(eventDate))){
                            //Log.d("firebase", selectedDate + "   ==   " + event.get_date() );
                            Log.d("firebase", event.get_title());

                            eventsList.add(event);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message
                // when we get any error from Firebase.
                Log.d("firebase", "Error : " + e.toString());
            } ;
            */

        events.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SimpleDateFormat BASIC_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Iterable<DataSnapshot> children = snapshot.getChildren();
                //Log.d("firebase", "Selected date: " + BDD_FORMAT.format(selectedDate));

                for (DataSnapshot child : children) {
                    Event event = child.getValue(Event.class);
                    //Log.d("firebase", event.get_title());
                    try {
                        Date eventDate = BASIC_FORMAT.parse(event.get_date());
                        //Log.d("firebase", BDD_FORMAT.format(eventDate));
                        if(BDD_FORMAT.format(selectedDate).equals(BDD_FORMAT.format(eventDate))){
                            //Log.d("firebase", selectedDate + "   ==   " + event.get_date() );
                            Log.d("firebase", event.get_title());

                            eventsList.add(event);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



/*
        eventsList.add(new Event("Eren", "Meeting 3", new Date(2021, 02, 23).toString()));
        eventsList.add(new Event("Eren", "Meeting 3", new Date(2021, 02, 23).toString()));
        eventsList.add(new Event("Eren", "Meeting 3", new Date(2021, 02, 23).toString()));
        eventsList.add(new Event("Eren", "Meeting 3", new Date(2021, 02, 23).toString()));
        eventsList.add(new Event("Eren", "Meeting 3", new Date(2021, 02, 23).toString()));
        eventsList.add(new Event("Eren", "Meeting 3", new Date(2021, 02, 23).toString()));

 */



        //List of daily events
        ListView eventListView = findViewById(R.id.eventListView);
        EventAdapter eventAdapter = new EventAdapter(this, eventsList);
        eventListView.setAdapter(eventAdapter);


        ArrayList<String> list = new ArrayList<>();
        dates.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    try {
                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        //Log.i("Firebase", map.get("-MUifXrF2rHVcNIJW7Dm").toString());
                    } catch (java.lang.NullPointerException e){
                        Log.d("Firebase", "This event is not today or don't exist");
                    } catch (Exception e) {
                        Log.d("Firebase", e.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
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
                    selectedDate = new Date(year, month, dayOfMonth);

                    events.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            SimpleDateFormat BASIC_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                            Iterable<DataSnapshot> children = snapshot.getChildren();
                            Log.d("firebase", "Selected date: " + BDD_FORMAT.format(selectedDate));

                            for (DataSnapshot child : children) {
                                Event event = child.getValue(Event.class);
                                try {
                                    Date eventDate = BASIC_FORMAT.parse(event.get_date());
                                    //Log.d("firebase", BDD_FORMAT.format(eventDate));
                                    if(BDD_FORMAT.format(selectedDate).equals(BDD_FORMAT.format(eventDate))){
                                        //Log.d("firebase", selectedDate + "   ==   " + event.get_date() );
                                        Log.d("firebase", event.get_title());

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




            //Pass date to next screen
            Intent intent = new Intent(view.getContext(), AddEventActivity.class);
            intent.putExtra("date", selectedDate);
            startActivity(intent);
        });


    }

}

