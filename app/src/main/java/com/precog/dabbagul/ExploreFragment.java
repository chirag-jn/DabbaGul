package com.precog.dabbagul;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExploreFragment extends BaseFragment {

    public TextView box1;
    public TextView box2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        box1 = view.findViewById(R.id.textView1);
//        box2 = view.findViewById(R.id.textView2);
        double[] vals = BaseActivity.myLocation.getCoordinates();
//        box1.setText(vals[0]+"");
//        box2.setText(vals[1]+"");
    }
}
