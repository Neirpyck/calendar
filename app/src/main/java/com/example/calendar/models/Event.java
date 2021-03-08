package com.example.calendar.models;

import java.util.ArrayList;

public class Event {
    private String _creator;
    private String _title;
    private String _date;
    //private ArrayList<User> _participants;


    public Event(String creator, String title, String date) {
        _date = date;
        _creator = creator;
        _title = title;
    }

    public Event(){}

    public Event(String creator, String title, String date,ArrayList<User> participants) {
        _date = date;
        _creator = creator;
        _title = title;
     //   _participants = participants;
    }

    /*
    public ArrayList<User> get_participants() {
        return _participants;
    }

     */

    public String get_date() {
        return _date;
    }

    public String get_creator() {
        return _creator;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    /*
    public void set_participants(User participant, int position) {
        this._participants.set(position, participant);
    }
    */

}
