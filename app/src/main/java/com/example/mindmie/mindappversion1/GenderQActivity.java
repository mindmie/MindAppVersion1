package com.example.mindmie.mindappversion1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GenderQActivity extends AppCompatActivity {
    //view
    private Spinner spnGender;
    private ProgressDialog progressDialog;

    //auth
    private FirebaseAuth mAuth;
    //realtime db
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_q);


        //view
        progressDialog = new ProgressDialog(this);
        spnGender = findViewById(R.id.spn_gender);

        //Auth
        mAuth = FirebaseAuth.getInstance();

        //realtime db
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initView();
    }

    //[start btn save and goto next question activity]
    private void initView() {

        //[start btn save]
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gender = spnGender.getSelectedItem().toString();

                //realtime
                String userID = mAuth.getCurrentUser().getUid();
                DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("gender");

                currentUser.setValue(gender);


                Toast.makeText(GenderQActivity.this, "successful", Toast.LENGTH_SHORT).show();


                // intent gender to Activity factor
                Intent i = new Intent( GenderQActivity.this , ActivityFactorQActivity.class);
                i.putExtra("gender", gender);
                startActivity(i);

            }
        }); //[end btn save]
    }//[end btn save and goto next question activity]
}
