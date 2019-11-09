package com.precog.dabbagul;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class RequestAdapter extends ArrayAdapter<Request> {
    Context mContext;
    Fragment parent;

    public RequestAdapter(Fragment parent, Context context, ArrayList<Request> requests) {
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
//                Fragment newFragment = new RequestAcceptFragment();
//                FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.main_activity_frame, newFragment).commit();
                showSortPopup();


            }
        });


        // Return the completed view to render on screen
        return convertView;
    }

    private void showSortPopup() {
        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_request_accept, null);

        PopupWindow changeSortPopUp;
        // Creating the PopupWindow
        changeSortPopUp = new PopupWindow(mContext);
        changeSortPopUp.setContentView(layout);
        changeSortPopUp.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setFocusable(true);

//        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
//        int OFFSET_X = -20;
//        int OFFSET_Y = 95;

        // Clear the default translucent background
//        changeSortPopUp.
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.CENTER, 0, 0);


        // Getting a reference to Close button, and close the popup when clicked.
//        Button close = (Button) layout.findViewById(R.id.close);
//        close.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                changeSortPopUp.dismiss();
//            }
//        });

    }

}


