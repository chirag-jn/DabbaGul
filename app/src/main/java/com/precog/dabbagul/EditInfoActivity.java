package com.precog.dabbagul;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class EditInfoActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private ImageButton confirm;
    private ImageButton cancel;
    private EditText name;
    private EditText age;
    private Spinner gender_spinner;
    private EditText institution;
    private ImageView dp;
    private static String TAG = "EditInfoActivity";
    private String gender = myProfileObj.gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        logv(TAG, "activity started");
        getSupportActionBar().hide();
        confirm = findViewById(R.id.edit_confirm);
        cancel = findViewById(R.id.edit_cancel);
        name = findViewById(R.id.edit_name);
        age = findViewById(R.id.edit_age);
        gender_spinner = findViewById(R.id.edit_spinner);
        institution = findViewById(R.id.edit_institution);
        dp = findViewById(R.id.profile_image);
        logv(TAG, "flag");

        name.setText(myProfileObj.name);
        age.setText(String.valueOf((int)myProfileObj.age));
        institution.setText(myProfileObj.institution);

        Picasso.get().load(myProfileObj.dp).into(dp);


        if(myProfileObj.gender!= null) {
            if (myProfileObj.gender.equals("Male") || myProfileObj.gender.equals("Female") || myProfileObj.gender.equals("Other"))
                gender_spinner.setSelection(((ArrayAdapter) gender_spinner.getAdapter()).getPosition(myProfileObj.gender));
        }

        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        gender_spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        gender = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_confirm:
                myProfileObj.name = name.getText().toString();
                System.out.println("name: "+myProfileObj.name);
                myProfileObj.age = Double.valueOf(age.getText().toString());
                myProfileObj.gender = gender;
                myProfileObj.institution = institution.getText().toString();
                profilesDB.document(myProfileObj.email).set(myProfileObj);
                System.out.println("age: "+myProfileObj.age);
                System.out.println("email: "+myProfileObj.email);
                System.out.println("gender: "+myProfileObj.gender);
                System.out.println("institution: "+myProfileObj.institution);
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.edit_cancel:
                finish();
                break;
            default:
                logv(TAG, "onClick: Kya click kar rhe ho");
                break;
        }
    }

}
