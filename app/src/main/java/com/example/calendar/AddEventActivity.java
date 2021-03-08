package com.example.calendar;

import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar.models.Event;
import com.example.calendar.models.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {
    //TODO: User name
    String user = "Cyp";

    // Get the database instance and store into object
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference events = database.getReference("events");
    DatabaseReference dates = database.getReference("dates");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //Get the date from previous screen
        Intent intent = getIntent();
        Date date = (Date) intent.getSerializableExtra("date");

        //Set TextInputField to date
        //TODO: WTF 3921 ??
        TextView dateTextView = findViewById(R.id.dateTextView);
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d MMMM y ");
        dateTextView.setText(DATE_FORMAT.format(date).toString());
        SimpleDateFormat BDD_FORMAT = new SimpleDateFormat("dd-MM-y");


        Button createButton = (Button) findViewById(R.id.btCreateEvent);
        createButton.setOnClickListener((v -> {
            Event event =  new Event(user, "", date.toString());

            //Get title
            EditText titleInput = findViewById(R.id.ipTitle);
            String title = titleInput.getText().toString();
            event.set_title(title);

            //TODO: What if 2 entry with same title ?
            //TODO: Only date in the future ?
            //Write both in Dates and Events
            String key = events.push().getKey();
            events.child(key).setValue(event);
            dates.child(BDD_FORMAT.format(date).toString()).child(key).setValue(events.push().getKey());

            //Go back to CalendarActivity
            finish();


        }));


    }

}