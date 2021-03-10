package com.example.calendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.calendar.R;
import com.example.calendar.models.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ParticipantsAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> participantsList;
    private final LayoutInflater inflater;


    public ParticipantsAdapter(Context context, List<String> participantsList) {
        this.context = context;
        this.participantsList = participantsList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return participantsList.size();
    }

    @Override
    public Object getItem(int position) {
        return participantsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.participant_item, null);
        TextView textView = (TextView) view.findViewById(R.id.participantsTextView);
        textView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        Button button = (Button) view.findViewById(R.id.button);



        return view;
    }
}
