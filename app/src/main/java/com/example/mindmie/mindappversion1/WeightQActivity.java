package com.example.mindmie.mindappversion1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WeightQActivity extends AppCompatActivity {

    //view
    private EditText etWeight;


    private ProgressDialog progressDialog;

    //auth
    private FirebaseAuth mAuth;
    //realtime db
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_q);

        //view
        etWeight = findViewById(R.id.et_weight);
        progressDialog = new ProgressDialog(this);

        //Auth
        mAuth = FirebaseAuth.getInstance();

        //realtime db
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initView();

    }

    //[start ini view]
    private void initView() {

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weight = etWeight.getText().toString().trim();

                //realtime
                String userID = mAuth.getCurrentUser().getUid();
                DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("weight");

                currentUser.setValue(weight);
                Toast.makeText(WeightQActivity.this, "successful", Toast.LENGTH_SHORT).show();

                //
                startActivity( new Intent(WeightQActivity.this , HeightQActivity.class ));
            }
        });
    } //[end ini view]
}
