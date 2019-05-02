package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.mskurt.neveremptylistviewlibrary.NeverEmptyListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentsList extends AppCompatActivity {

    @BindView(R.id.students_list)
    NeverEmptyListView _studentsList;
    ArrayList<String> students;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        ButterKnife.bind(this);
        final String school = getIntent().getStringExtra("school");
        final String year = getIntent().getStringExtra("year");
        String subject = getIntent().getStringExtra("subject");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("system").child("schools");
        final ProgressDialog progressDialog = new ProgressDialog(StudentsList.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("loading data...");
        progressDialog.show();


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                students = new ArrayList<>();

                dataSnapshot = dataSnapshot.child(school).child(year.trim());

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                     students.add(ds.child("name").getValue().toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(StudentsList.this, android.R.layout.simple_list_item_1, android.R.id.text1, students);
                _studentsList.setAdapter(adapter);
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();

            }
        });


    }
}
