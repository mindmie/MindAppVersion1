package com.example.mindmie.mindappversion1;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AgeQActivity extends AppCompatActivity {

    //view
    private String date;
    private TextView displayDate;
    private int birthYear;
    private int birthMonth;
    private int birthDay;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    private ProgressDialog progressDialog;

    //auth
    private FirebaseAuth mAuth;
    //realtime db
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_q);

        progressDialog = new ProgressDialog(this);
        displayDate = findViewById(R.id.et_date);

        //Auth
        mAuth = FirebaseAuth.getInstance();

        //realtime db
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initView();
    }

    //[start init view]
    private void initView() {

        //[start date picker]
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int currentYear = cal.get(Calendar.YEAR);
                int currentMonth = cal.get(Calendar.MONTH);
                int currentDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(
                        AgeQActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        currentYear,currentMonth,currentDay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                birthYear = year; // birth year after pick by user
                birthMonth = month;
                birthDay = day;

                //date format
                date = day + "/" + month + "/" + year;
                displayDate.setText(date);
            }

        }; //[end date picker]

        //[start btn save and goto next question activity]
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //age calculation
                Calendar calendar = Calendar.getInstance();
                int currentAge = calendar.get(Calendar.YEAR) - birthYear;   // age is integet , isnt string so when use it call as integer na

                //realtime
                String userID = mAuth.getCurrentUser().getUid();
                DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("age");

               // Map addData = new HashMap();
//                addData.put("age", currentAge );   //displayDate.getText().toString() this is date format

                //currentUser.setValue(currentAge);  //displayDate.getText().toString() this is date format
                currentUser.setValue(displayDate.getText().toString());
                Toast.makeText(AgeQActivity.this, "successful", Toast.LENGTH_SHORT).show();

                // go to next question
                startActivity( new Intent( AgeQActivity.this ,GenderQActivity.class));
            }
        });//[end btn save and goto next question activity]



    }//[end init view]
}
