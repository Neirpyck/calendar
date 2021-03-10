package com.example.calendar.models;

import java.util.ArrayList;

public class Event {
    private String _creator;
    private String _title;
    private String _date;
    private ArrayList<String> _participants;
    private boolean _isRappel;


    public Event(String creator, String title, String date, ArrayList<String> participants, boolean isRappel) {
        _date = date;
        _creator = creator;
        _title = title;
        _participants = participants;
        _isRappel = isRappel;
    }

    public Event(){}


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

    public boolean get_isRappel() { return _isRappel; }

    public ArrayList<String> get_participants() { return _participants;}

    public void set_title(String _title) {
        this._title = _title;
    }

    public void set_isRappel(Boolean _isRappel) {this._isRappel = _isRappel;}


}
