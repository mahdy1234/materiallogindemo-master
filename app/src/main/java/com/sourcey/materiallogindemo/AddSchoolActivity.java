package com.sourcey.materiallogindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;

public class AddSchoolActivity extends AppCompatActivity {

    @BindView(R.id.school_name)
    EditText schoolName;


    @BindView(R.id.add_school)
    Button addSchool;


    FirebaseDatabase database;
    DatabaseReference myRef;
    SearchableSpinner schoolType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school);
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("system").child("schoolsData");
        final ArrayList<String> data = new ArrayList<>();
        schoolType = findViewById(R.id.school_type);
        data.add("Primary");
        data.add("preparatory");
        data.add("Secondary");
        schoolType.setAdapter(new SimpleArrayListAdapter(this,data));
        addSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!validate()) {

                    return;
                }
                String schoolNameString = schoolName.getText().toString();
                String schoolTypeString = schoolType.getSelectedItem().toString();
                HashMap<String, String> schoolDataMap = new HashMap<>();
                schoolDataMap.put("schoolName", schoolNameString);
                schoolDataMap.put("type", schoolTypeString);
                myRef.child(schoolNameString).setValue(schoolDataMap);
                finish();
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        if (schoolName.getText().toString().isEmpty()) {

            schoolName.setError("can't be empty");

        } else {
            schoolName.setError(null);
        }

        return valid;
    }
}
