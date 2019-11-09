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

import java.util.HashMap;

public class ExploreFragment extends BaseFragment implements View.OnClickListener {

    LinearLayout addYourLunch;
    TextView addYourLunchTitle;

    private static String TAG = "ExploreFragment";

    private static final int ADD_LUNCH_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        double[] vals = BaseActivity.myLocation.getCoordinates();
        addYourLunch = view.findViewById(R.id.add_your_lunch_layout);
        addYourLunchTitle = view.findViewById(R.id.add_your_lunch_title);
        addYourLunch.setOnClickListener(this);
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
                HashMap<String, String> map = new HashMap<>();
                map.put("name", dishName);
                map.put("description", description);
                map.put("image", lunchImagePath);
                db.collection("chirag").add(map);
                addYourLunchTitle.setText(R.string.edit_your_lunch);
            }
        }
    }
}
