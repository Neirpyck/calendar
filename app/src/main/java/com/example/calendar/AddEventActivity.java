package com.example.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar.adapter.ParticipantsAdapter;
import com.example.calendar.models.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity {
    //TODO: User name
    String user = "Cyp";

    // Get the database instance and store into object
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference events = database.getReference("events");
    DatabaseReference dates = database.getReference("dates");
    ArrayList<String> participantsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        participantsList.add("Cyp");
        ListView participantsListView = findViewById(R.id.participantsListView);
        ParticipantsAdapter participantAdapter = new ParticipantsAdapter(this, participantsList);
        participantsListView.setAdapter(participantAdapter);




        //Get the date from previous screen
        Intent intent = getIntent();
        //Date date = (Date) intent.getSerializableExtra("date");
        Date date = new Date();
        date.setTime(intent.getLongExtra("date", -1));

        //Set TextInputField to date
        TextView dateTextView = findViewById(R.id.dateTextView);
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d MMMM Y ");
        dateTextView.setText(DATE_FORMAT.format(date));
        SimpleDateFormat BDD_FORMAT = new SimpleDateFormat("dd-MM-Y");




        Button createButton = (Button) findViewById(R.id.btCreateEvent);
        createButton.setOnClickListener((v -> {
            Event event =  new Event(user, "", date.toString(), new ArrayList<String>(), false);

            //Get title
            EditText titleInput = findViewById(R.id.ipTitle);
            String title = titleInput.getText().toString();
            event.set_title(title);

            //Get is Rappel
            CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox2);
            Boolean isRappel = checkBox.isChecked();
            event.set_isRappel(isRappel);

            //Create event on DB
            event.get_date();
            events.child(event.get_date()).setValue(event);

            //Go back to CalendarActivity
            finish();


        }));


    }

}