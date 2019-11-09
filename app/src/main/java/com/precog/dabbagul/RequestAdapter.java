package com.precog.dabbagul;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class RequestAdapter extends ArrayAdapter<Request> {
    Context mContext;

    public RequestAdapter(Context context, ArrayList<Request> requests) {
        super(context, 0, requests);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Request request = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_request_item, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.request_sender_name);
        TextView food = (TextView) convertView.findViewById(R.id.request_food_name);
        TextView distance = (TextView) convertView.findViewById(R.id.request_distance);

        Log.e("CHECK", "yo: " + request.sender_email + " " + request.receiver_email);

        // Populate the data into the template view using the data object
        name.setText(request.sender_name);
        food.setText(request.sender_food);
        distance.setText("" + request.distance);

        // Return the completed view to render on screen
        return convertView;
    }

}
