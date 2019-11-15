package com.precog.dabbagul;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FoodAdapter extends ArrayAdapter<Food> {

    public static final String TAG = "FoodAdapter";

    Context mContext;
    Fragment parent;
    ArrayList<Food> lunches;


    public FoodAdapter(Fragment parent, Context context, ArrayList<Food> lunches) {
        super(context, 0, lunches);
        mContext = context;
        this.lunches = lunches;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Food lunch = lunches.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_explore_item, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.explore_lunch_owner);
        TextView food = (TextView) convertView.findViewById(R.id.explore_food_name);
        TextView distance = (TextView) convertView.findViewById(R.id.explore_food_distance);
        TextView time = (TextView) convertView.findViewById(R.id.explore_food_time);
        TextView tags = (TextView) convertView.findViewById(R.id.explore_food_tags);
        ImageView food_image = (ImageView) convertView.findViewById(R.id.explore_food_image);
        LinearLayout item_layout = (LinearLayout) convertView.findViewById(R.id.explore_layout);
        Log.e(TAG, this + " " + lunch.name + " " + lunch.ownerID);

        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        String timeString = formatter.format(new Date(lunch.time));

        // Populate the data into the template view using the data object
        name.setText(lunch.ownerName);
        food.setText(lunch.name);
        //distance.setText("" );
        time.setText(timeString);
        String tags_string = "";

        for (int i = 0; i < lunch.tags.size(); i++) {
            tags_string.concat("#" + lunch.tags.get(i) + " ");
        }

        tags.setText(tags_string);
        Picasso.get().load(lunch.foodPhoto).into(food_image);

        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                        Bundle args = new Bundle();
                Intent intent = new Intent(mContext, SingleFoodActivity.class);
                intent.putExtra("receiver_id", lunch);
//                        args.putSerializable("receiver_id", lunch);
                mContext.startActivity(intent);
//                        SendRequestFragment f = new SendRequestFragment();
//                        f.setArguments(args);
//                        ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.explore_frame, f).addToBackStack(null).commit();
            }
        });
        //Return the completed view to render on screen
        return convertView;
    }

}
