package com.precog.dabbagul;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RequestAcceptDialog extends Dialog implements View.OnClickListener {
    public Context c;
    public Dialog d;
    String senderProfileName;
    TextView profileName;
    Button sendAMessage;
    ImageView cancel;

    public RequestAcceptDialog(Context a, String senderProfileName) {
        super(a);
        this.c = a;
        this.senderProfileName = senderProfileName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_request_accept);
        getWindow().setBackgroundDrawableResource(R.drawable.popup_outside);
        profileName = (TextView) findViewById(R.id.profile_name);
        sendAMessage = (Button) findViewById(R.id.send_message_button);
        cancel = (ImageView) findViewById(R.id.request_accept_cancel);
        profileName.setText(senderProfileName);
        sendAMessage.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message_button:
                // TODO:
                break;
            case R.id.cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
