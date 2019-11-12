package com.precog.dabbagul;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ExploreFragment extends BaseFragment implements View.OnClickListener {

    LinearLayout addYourLunch;
    TextView addYourLunchTitle;

    private static String TAG = "ExploreFragment";

    private static final int ADD_LUNCH_REQUEST = 1;

    double[] coordinates;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        coordinates = myLocation.getCoordinates();
        addYourLunch = view.findViewById(R.id.add_your_lunch_layout);
        addYourLunchTitle = view.findViewById(R.id.add_your_lunch_title);
        addYourLunch.setOnClickListener(this);

        if(myProfileObj.currentItem!=null) {
            addYourLunchTitle.setText(R.string.edit_your_lunch);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.add_your_lunch_layout:
                intent = new Intent(getActivity(), AddLunchActivity.class);
//                getActivity().startActivity(intent);
                startActivityForResult(intent, ADD_LUNCH_REQUEST);
                break;
            default:
                logv(TAG, "kya kar rhe ho " + view.getId());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_LUNCH_REQUEST) {
            if(resultCode == getActivity().RESULT_OK) {
                logv(TAG, "ab toh result aa gya");
//                addYourLunch.setVisibility(View.GONE);
                String dishName = data.getStringExtra("name");
                String description = data.getStringExtra("description");
                String lunchImagePath = data.getStringExtra("image");
                Food item = (Food) data.getSerializableExtra(AddLunchActivity.ADD_FOOD_CODE);
//                HashMap<String, String> map = (HashMap<String, String>) myProfileObj.currentItem;

                item.latitude = coordinates[0];
                item.longitude = coordinates[1];

                long time = System.currentTimeMillis();
                item.time = time;

                myProfileObj.currentItem = item;

                if(myProfileObj.history==null) {
                    myProfileObj.history = new ArrayList<>();
                }

                myProfileObj.history.add(item);

//                map.put("name", dishName);
//                map.put("description", description);
//                map.put("image", lunchImagePath);
//                coordinates = myLocation.getCoordinates();


//                map.put("latitude", coordinates[0]+"");
//                map.put("longitude", coordinates[1]+"");

//                map.put("time", time+"");
                profilesDB.document(myProfileObj.email).set(myProfileObj);
                foodDB.add(item);
//                db.collection(myProfileObj.email).add(map);
                addYourLunchTitle.setText(R.string.edit_your_lunch);
            }
        }
    }
}
