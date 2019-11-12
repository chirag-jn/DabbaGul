package com.precog.dabbagul;

import android.Manifest;
import android.app.ActionBar;
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
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.desmond.squarecamera.CameraActivity;
import com.desmond.squarecamera.ImageUtility;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nex3z.flowlayout.FlowLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddLunchActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AddLunchActivity";
    ImageButton addLunchConfirm;
    ImageButton addLunchClose;
    ImageButton addLunchImage;
    ImageView lunchImage;
    EditText dishName;
    EditText description;
    FlowLayout tagsLayout;

    public static final String ADD_FOOD_CODE = "AddFood";

    String lunchImagePath;

    private ArrayList<String> selectedTags;

    public static final int CAMERA_REQUEST = 1;

    String foodName;

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
        tagsLayout = findViewById(R.id.tags);

        selectedTags = new ArrayList<>();

        addLunchConfirm.setOnClickListener(this);
        addLunchClose.setOnClickListener(this);
        addLunchImage.setOnClickListener(this);

        if(myProfileObj.currentItem!=null) {
            Picasso.get().load(myProfileObj.currentItem.foodPhoto).into(lunchImage);
            dishName.setText(myProfileObj.currentItem.name);
            description.setText(myProfileObj.currentItem.description);
        }

        addTags();
    }

    private void addTags() {
        Map<String, Boolean> isSelected = new HashMap<>();

//        int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, this.getResources().getDisplayMetrics());

        for(int i=0; i<tags.length; i++) {
            isSelected.put(tags[i], false);
        }

        if(myProfileObj.currentItem!=null) {
            for (int i=0; i<myProfileObj.currentItem.tags.size(); i++) {
                isSelected.remove(myProfileObj.currentItem.tags.get(i));
                isSelected.put(myProfileObj.currentItem.tags.get(i), true);
                selectedTags.add(myProfileObj.currentItem.tags.get(i));
            }
        }

        final int tagsCount = tagsLayout.getChildCount();
        for (int i=0; i<tagsCount; i++) {
            Button button = (Button) tagsLayout.getChildAt(i);

            if(isSelected.get(button.getText())) {
                button.setTextColor(getColor(R.color.tag_selected));
                button.setBackground(getDrawable(R.drawable.tag_selected));
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text = (String) button.getText();
                    if(isSelected.get(text)) {
                        isSelected.remove(text);
                        isSelected.put(text, false);
                        selectedTags.remove(text);
                        button.setTextColor(getColor(R.color.tag_not_selected));
                        button.setBackground(getDrawable(R.drawable.tags_border));
                    } else {
                        isSelected.remove(text);
                        isSelected.put(text, true);
                        selectedTags.add(text);
                        button.setTextColor(getColor(R.color.tag_selected));
                        button.setBackground(getDrawable(R.drawable.tag_selected));
                    }

                }
            });
        }

//        for(int i=0; i<tags.length; i++) {
//            Button button = new Button(this);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 30*dp);
//            params.setMargins(10, 5, 0, 0);
//            button.setLayoutParams(params);
//            button.setBackground(getDrawable(R.drawable.tags_border));
//            button.setText(tags[i]);
//            button.setTextColor(getColor(R.color.tag_not_selected));
//            button.setTextSize(15);
//            button.setAllCaps(false);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String text = (String) button.getText();
//                    if(isSelected.get(text)) {
//                        isSelected.remove(text);
//                        isSelected.put(text, false);
//                        selectedTags.remove(text);
//                        button.setTextColor(getColor(R.color.tag_not_selected));
//                        button.setBackground(getDrawable(R.drawable.tags_border));
//                    } else {
//                        isSelected.remove(text);
//                        isSelected.put(text, true);
//                        selectedTags.add(text);
//                        button.setTextColor(getColor(R.color.tag_selected));
//                        button.setBackground(getDrawable(R.drawable.tag_selected));
//                    }
//
//                }
//            });
//            tagsLayout.addView(button);
//        }
    }

    private void returnIntent() {
        showLoading("Adding Data...");
        Intent returnInd = new Intent();
        logv(TAG, "result is ok my friend");
        // TODO: Add Result here
//        returnInd.putStringArrayListExtra()

        Uri stream = Uri.fromFile(new File(lunchImagePath));

        final StorageReference imgStorage = imageStorage.child("food/" + foodName);
        imgStorage.putFile(stream).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUri = uri.toString();

                        // TODO: Add Time here in Milliseconds in place of System.currentTimeMillis

                        Food newFood = new Food(dishName.getText().toString(), description.getText().toString(), System.currentTimeMillis(), downloadUri, selectedTags);
                //        returnInd.putExtra("name", dishName.getText().toString());
                //        returnInd.putExtra("description", description.getText().toString());
                //        returnInd.putExtra("image", lunchImagePath);
                //        returnInd.putExtra("food", newFood);
                        returnInd.putExtra(ADD_FOOD_CODE, newFood);
                        setResult(Activity.RESULT_OK, returnInd);
                        hideLoading();
                        finish();
                    }
                });
            }
        });


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
        if(dishName.getText().length()!=0 && description.getText().length()!=0 && lunchImagePath.length()!=0 && foodName.length()!=0) {
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
                foodName = fileName;
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