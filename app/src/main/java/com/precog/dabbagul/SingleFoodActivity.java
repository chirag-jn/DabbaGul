package com.precog.dabbagul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.squareup.picasso.Picasso;

public class SingleFoodActivity extends BaseActivity implements View.OnClickListener {

    private static String TAG = "SingleFoodActivity";
    double distance;
    ImageView food_image;
    ImageButton close;
    TextView food_name;
    TextView food_distance;
    TextView person_name;
    TextView time;
    TextView person_institution;
    TextView food_description;
    TextView food_tags;
    Button send_request;
    UserProfile receiver;
    Food requested_lunch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);

        getSupportActionBar().hide();


        food_image = findViewById(R.id.request_food_image);
        food_name = findViewById(R.id.request_food_name);
        food_distance = findViewById(R.id.request_distance);
        person_name = findViewById(R.id.request_sender_name);
        time = findViewById(R.id.request_time);
        person_institution = findViewById(R.id.request_institution);
        food_description = findViewById(R.id.request_food_description);
        food_tags = findViewById(R.id.request_tags);
        send_request = findViewById(R.id.send_request);
        send_request.setOnClickListener(this);
        close = findViewById(R.id.request_close);
        close.setOnClickListener(this);

        Intent intent = getIntent();

        requested_lunch = (Food) intent.getSerializableExtra("receiver_id");

        Picasso.get().load(requested_lunch.foodPhoto).into(food_image);
        food_name.setText(requested_lunch.name);
        //person_name.setText(receiver.name);
        time.setText(String.valueOf(requested_lunch.time));
        //person_institution.setText(receiver.institution);
        food_description.setText(requested_lunch.description);
        String tags_string = "";
        for (int i = 0; i < requested_lunch.tags.size(); i++) {
            tags_string.concat("#" + requested_lunch.tags.get(i) + " ");}

        food_tags.setText(tags_string);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.request_close:
                finish();
                break;

            case R.id.send_request:
                Request req = new Request();
                req.sender_email = myProfileObj.email;
                req.date_generated = Timestamp.now();
                req.receiver_email = receiver.email;
                //req.sender_food = myProfileObj.currentItem.get("name");
                req.sender_name = myProfileObj.name;
                req.status = 0;

            default:
                logv(TAG, "kya kar rhe ho " + view.getId());
                break;
        }
    }
}
