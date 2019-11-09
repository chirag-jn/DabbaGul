package com.precog.dabbagul;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.desmond.squarecamera.CameraActivity;
import com.desmond.squarecamera.ImageUtility;

import java.util.ArrayList;

public class AddLunchActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AddLunchActivity";
    ImageButton addLunchConfirm;
    ImageButton addLunchClose;
    ImageButton addLunchImage;
    ImageView lunchImage;
    EditText dishName;
    EditText description;

    String lunchImagePath;

    public static final int CAMERA_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lunch);

        getSupportActionBar().hide();

        logv(TAG, "hi you reached here, congrats");
        addLunchConfirm = findViewById(R.id.add_lunch_confirm);
        addLunchClose = findViewById(R.id.add_lunch_close);
        addLunchImage = findViewById(R.id.add_lunch_image);
        lunchImage = findViewById(R.id.lunch_image);
        dishName = findViewById(R.id.lunch_name);
        description = findViewById(R.id.lunch_description);

        addLunchConfirm.setOnClickListener(this);
        addLunchClose.setOnClickListener(this);
        addLunchImage.setOnClickListener(this);
    }

    private void returnIntent() {
        Intent returnInd = new Intent();
        logv(TAG, "result is ok my friend");
        // TODO: Add Result here
//        returnInd.putStringArrayListExtra()
        returnInd.putExtra("name", dishName.getText().toString());
        returnInd.putExtra("description", description.getText().toString());
        returnInd.putExtra("image", lunchImagePath);
        setResult(Activity.RESULT_OK, returnInd);
        finish();
    }

    private void returnIntentWithoutData() {
        Intent returnInd = new Intent();
        logv(TAG, "result is not ok my friend");
        // TODO: Add Result here
//        returnInd.putExtra("result", "fill here");
        setResult(Activity.RESULT_CANCELED, returnInd);
        finish();
    }

    private boolean checkVals() {
        if(dishName.getText().length()!=0 && description.getText().length()!=0 && lunchImagePath.length()!=0) {
            return true;
        }
        return false;
    }

    private void cameraIntent() {
        Intent intent = new Intent(this, CameraCaptureActivity.class);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_OK) return;
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                logv(TAG, "image captured");
                String fileName = data.getStringExtra("filename");
                lunchImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/" + R.string.app_folder + "/" + fileName + ".jpg";
                Bitmap bitmap = BitmapFactory.decodeFile(lunchImagePath);
                lunchImage.setImageBitmap(bitmap);
                logv(TAG, fileName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_lunch_confirm:
                if(checkVals()) {
                    returnIntent();
                } else {
                    Toast.makeText(this, "Please Add Content", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_lunch_close:
                returnIntentWithoutData();
                break;
            case R.id.add_lunch_image:
                cameraIntent();
                break;
            default:
                break;
        }
    }
}