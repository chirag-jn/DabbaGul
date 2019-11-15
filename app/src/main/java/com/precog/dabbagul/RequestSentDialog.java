package com.precog.dabbagul;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RequestSentDialog extends Dialog implements View.OnClickListener {
    TextView popupText;
    Button okay;

    public RequestSentDialog(Context a) {
        super(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_okay_feedback);
        getWindow().setBackgroundDrawableResource(R.drawable.popup_outside);
        popupText = findViewById(R.id.popup_text);
        popupText.setText("Request Sent!");
        okay = findViewById(R.id.popup_button);
        okay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_text:
                dismiss();
            default:
                break;
        }
        dismiss();
    }
}
