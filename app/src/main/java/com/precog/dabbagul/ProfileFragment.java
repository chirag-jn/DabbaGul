package com.precog.dabbagul;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private static String TAG = "ProfileFragment";

    TextView profileName;
    ImageView profileImage;
    LinearLayout lunchHistory;
    LinearLayout editInfo;
    LinearLayout settings;

    Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileName = view.findViewById(R.id.profile_name);
        profileImage = view.findViewById(R.id.profile_image);
        lunchHistory = view.findViewById(R.id.profile_history_button);
        editInfo = view.findViewById(R.id.profile_edit_button);
        settings = view.findViewById(R.id.profile_settings_button);
        lunchHistory.setOnClickListener(this);
        editInfo.setOnClickListener(this);
        settings.setOnClickListener(this);

        profileName.setText(myProfileObj.name);

//        Fresco.initialize(getActivity());

        Picasso.get().load(myProfileObj.dp).into(profileImage);

//        Uri dpUri = Uri.parse(myProfileObj.dp);
//        SimpleDraweeView myImage = (SimpleDraweeView) profileImage;
//        myImage.setImageURI(dpUri);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_history_button:
                break;
            case R.id.profile_edit_button:
                break;
            case R.id.profile_settings_button:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                // Don't finish here
                break;
            default:
                logv(TAG, "onClick: Kya click kar rhe ho");
                break;
        }
    }
}
