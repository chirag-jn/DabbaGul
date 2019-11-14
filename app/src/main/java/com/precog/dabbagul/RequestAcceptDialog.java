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

public class RequestAcceptDialog extends Dialog implements View.OnClickListener {
    public Context c;
    public Dialog d;
    Request request;
    TextView profileName;
    Button sendAMessage;
    ImageView cancel;

    public RequestAcceptDialog(Context a, Request request) {
        super(a);
        this.c = a;
        this.request = request;
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
        profileName.setText(request.sender_name);
        sendAMessage.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message_button:
                Intent intent = new Intent(c, SingleChatActivity.class);
                intent.putExtra(SingleChatActivity.CHAT_ROOM_ID, "Chat Room ID");
                intent.putExtra(SingleChatActivity.CHAT_ROOM_NAME, request.sender_name);
                intent.putExtra(SingleChatActivity.CHAT_ROOM_EMAIL, request.sender_email);
                intent.putExtra(SingleChatActivity.CHAT_ROOM_DP, BaseActivity.myProfileObj.dp); // TODO: Replace myProfilePbj with suitable request attribute of dp
                c.startActivity(intent);
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
