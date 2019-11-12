package com.precog.dabbagul;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.desmond.squarecamera.CameraActivity;
import com.desmond.squarecamera.ImageUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraCaptureActivity extends BaseActivity {

    private static final String TAG = "CameraCaptureActivity";

    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private Point mSize;

    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_camera_capture);

        logv(TAG, "hi you reached here, congrats");

        Display display = getWindowManager().getDefaultDisplay();
        mSize = new Point();
        display.getSize(mSize);

        requestForCameraPermission();
    }

    private void returnIntent() {
        Intent returnInd = new Intent();
        logv(TAG, "result is ok my friend");
        // TODO: Add Result here
        returnInd.putExtra("filename", fileName);
        setResult(Activity.RESULT_OK, returnInd);
        finish();
    }

    private void returnIntentWithoutResult() {
        Intent returnInd = new Intent();
        logv(TAG, "result is ok my friend");
        // TODO: Add Result here
//        returnInd.putExtra("filename", fileName);
        setResult(Activity.RESULT_CANCELED, returnInd);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_OK) returnIntentWithoutResult();

        if (requestCode == REQUEST_CAMERA) {
            logv(TAG, "flag5");
            Uri photoUri = data.getData();
            // Get the bitmap in according to the width of the device
            Bitmap bitmap = ImageUtility.decodeSampledBitmapFromPath(photoUri.getPath(), mSize.x, mSize.x);
//            ((ImageView) findViewById(R.id.image)).setImageBitmap(bitmap);
            logv(TAG, "flag6");
            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/" + R.string.app_folder;
            File dir = new File(file_path);
            if(!dir.exists())
                dir.mkdirs();

            try {
                fileName = myProfileObj.id + "-" + System.currentTimeMillis();
                File file = new File(dir, fileName + ".jpg");
                FileOutputStream fOut = new FileOutputStream(file);

                bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                fOut.flush();
                fOut.close();
                returnIntent();
            } catch (IOException e) {
                loge(TAG, "camera ki image save kaun karega");
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void requestForCameraPermission() {
        final String permission = Manifest.permission.CAMERA;
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showPermissionRationaleDialog("Test", permission);
            } else {
                requestForPermission(permission);
            }
        } else {
            logv(TAG, "flag1");
            launch();
        }
    }

    private void showPermissionRationaleDialog(final String message, final String permission) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestForPermission(permission);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    private void requestForPermission(final String permission) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_CAMERA_PERMISSION);
    }

    private void launch() {
        logv(TAG, "flag3");
        Intent startCustomCameraIntent = new Intent(this, CameraActivity.class);
        logv(TAG, "flag4");
        startActivityForResult(startCustomCameraIntent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                final int numOfRequest = grantResults.length;
                final boolean isGranted = numOfRequest == 1
                        && PackageManager.PERMISSION_GRANTED == grantResults[numOfRequest - 1];
                if (isGranted) {
                    logv(TAG, "flag2");
                    launch();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}