package com.precog.dabbagul;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class RequestAdapter extends ArrayAdapter<Request> {
    Context mContext;
    Fragment parent;
    ArrayList<Request> requests;

    public RequestAdapter(Fragment parent, Context context, ArrayList<Request> requests) {
        super(context, 0, requests);
        mContext = context;
        this.requests = requests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Request request = requests.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_request_item, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.request_sender_name);
        TextView food = (TextView) convertView.findViewById(R.id.request_food_name);
        TextView distance = (TextView) convertView.findViewById(R.id.request_distance);
        Button contact = (Button) convertView.findViewById(R.id.request_contact);
        Button remove = (Button) convertView.findViewById(R.id.request_remove);

        Log.e("CHECK", this + " " + request.sender_email + " " + request.receiver_email);

        // Populate the data into the template view using the data object
        name.setText(request.sender_name);
        food.setText(request.sender_food);
        distance.setText("" + request.distance);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestAcceptDialog dialog = new RequestAcceptDialog(mContext, request);
                dialog.show();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}