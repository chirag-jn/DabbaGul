package com.precog.dabbagul;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddLunchActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AddLunchActivity";
    ImageButton addLunchConfirm;
    ImageButton addLunchClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lunch);

        logv(TAG, "hi you reached here, congrats");
        addLunchConfirm = findViewById(R.id.add_lunch_confirm);
        addLunchClose = findViewById(R.id.add_lunch_close);

        addLunchConfirm.setOnClickListener(this);
        addLunchClose.setOnClickListener(this);
    }

    private void returnIntent() {
        Intent returnInd = new Intent();
        logv(TAG, "result is ok my friend");
        // TODO: Add Result here
        returnInd.putExtra("result", "fill here");
        setResult(Activity.RESULT_OK, returnInd);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_lunch_confirm:
                returnIntent();
                break;
            case R.id.add_lunch_close:
                finish();
                break;
            default:
                break;
        }
    }
}