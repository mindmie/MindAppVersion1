package com.example.mindmie.mindappversion1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ActivityFactorQActivity extends AppCompatActivity {
    //view
    private Spinner spnActivity;
    private String activityFactor;

    private ProgressDialog progressDialog;

    //auth
    private FirebaseAuth mAuth;
    //realtime db
    private DatabaseReference mDatabase;
    //firestore
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factor_q);

        spnActivity = findViewById(R.id.spn_activity);
        progressDialog = new ProgressDialog(this);

        //Auth
        mAuth = FirebaseAuth.getInstance();

        //realtime db
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initView();

    }

    //[start init view]
    private void initView() {

        Bundle bundle = getIntent().getExtras();
        final String gender = bundle.getString("gender");

        //[start btn save]
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String activity = spnActivity.getSelectedItem().toString();
                String userID = mAuth.getCurrentUser().getUid();

                DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("activity");


                if ( activity.equals("no exercise")){
                    activityFactor = "1.3";
                }
                else if ( activity.equals("1 - 3 day per week") && gender.equals("Man") ){
                    activityFactor = "1.6";
                }
                else if ( activity.equals("1 - 3 day per week") && gender.equals("Woman") ){
                    activityFactor = "1.5";
                }
                else if(activity.equals("3 - 5 day per week") && gender.equals("Man")){
                    activityFactor = "1.7";
                }
                else if(activity.equals("3 - 5 day per week") && gender.equals("Woman")){
                    activityFactor = "1.6";
                }
                else if (activity.equals("6 - 7 day per week") && gender.equals("Man")){
                    activityFactor = "2.1";
                }
                else if (activity.equals("6 - 7 day per week") && gender.equals("Woman")){
                    activityFactor = "1.9";
                }
                else if (activity.equals("2 time per day") && gender.equals("Man")){
                    activityFactor = "2.4";
                }
                else if (activity.equals("2 time per day") && gender.equals("Woman")){
                    activityFactor = "2.2";
                }



                //[start add value]
//                mDatabase.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String genderUser = dataSnapshot.getValue().toString();
//
//                        if ( activity.equals("no exercise")){
//                            activityFactor = "1.3";
//                        }
//                        else if ( activity.equals("1 - 2 day per week") && genderUser.equals("Man") ){
//                            activityFactor = "1.6";
//                        }
//                        else if ( activity.equals("1 - 2 day per week") && genderUser.equals("Woman") ){
//                            activityFactor = "1.5";
//                        }
//                        else if(activity.equals("3 - 4 day per week")){
//                            activityFactor = "1.6";
//                        }
//                        else if (activity.equals("5 - 6 day per week")){
//                            activityFactor = "1.6";
//                        }
//                        else if (activity.equals("everyday")){
//                            activityFactor = "1.6";
//                        }
//                        else if (activity.equals("2 time per day")){
//                            activityFactor = "1.6";
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                }); ////[end add value]


                currentUser.setValue(activityFactor);
                Toast.makeText(ActivityFactorQActivity.this, "successful", Toast.LENGTH_SHORT).show();

                startActivity( new Intent(ActivityFactorQActivity.this , WeightQActivity.class));

                //realtime





//                mDatabase.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String genderUser = dataSnapshot.getValue().toString();
//
//                        if (genderUser == "Man" && activity == "no exercise"){
//                            currentUser.setValue("1.3");
//                            Toast.makeText(ActivityFactorQActivity.this, "successful", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });





                //startActivity( new Intent(ActivityFactorQActivity.this , ));
            }

        }); //[end btn save]
    }//[end init view]
}
