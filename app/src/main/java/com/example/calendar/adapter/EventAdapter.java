package com.example.calendar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.calendar.R;
import com.example.calendar.models.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.graphics.Color.rgb;

public class EventAdapter extends BaseAdapter {

    private final Context context;
    private final List<Event> eventsList;
    private final LayoutInflater inflater;


    public EventAdapter(Context context, List<Event> eventsList) {
        this.context = context;
        this.eventsList = eventsList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return eventsList.size();
    }

    @Override
    public Event getItem(int position) {
        return eventsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.event_item, null);

        SimpleDateFormat BDD_FORMAT = new SimpleDateFormat("dd-MM-y");
        SimpleDateFormat BASIC_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        Event currentEvent = (Event) getItem(position);
        String eventTitle = currentEvent.get_title();
        String eventCreator = currentEvent.get_creator();
        Date eventDate;
        try {
            eventDate = BASIC_FORMAT.parse(currentEvent.get_date());
        } catch (ParseException e) {
            e.printStackTrace();
            eventDate = new Date();
        }


        TextView eventTitleView = view.findViewById(R.id.event_title);
        eventTitleView.setText(eventTitle);

        if (currentEvent.get_isRappel()) {
            eventTitleView.setTextColor(rgb(255, 0, 0));
            view.setOnClickListener(v -> {
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
            });
        } else {
            eventTitleView.setTextColor(rgb(0, 0, 0));
        }

        TextView eventCreatorView = view.findViewById(R.id.event_creator);
        eventCreatorView.setText(eventCreator);

        TextView eventDateView = view.findViewById(R.id.event_date);
        eventDateView.setText(BDD_FORMAT.format(eventDate));
        return view;
    }
}
