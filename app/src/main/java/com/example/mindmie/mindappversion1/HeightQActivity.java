package com.example.mindmie.mindappversion1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextDirectionHeuristic;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class HeightQActivity extends AppCompatActivity {

    //view
    private EditText etHeight;
    private ProgressDialog progressDialog;

    //auth
    private FirebaseAuth mAuth;
    //realtime db
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height_q);

        //view
        etHeight = findViewById(R.id.et_height);
        progressDialog = new ProgressDialog(this);

        //Auth
        mAuth = FirebaseAuth.getInstance();

        //realtime db
        mDatabase = FirebaseDatabase.getInstance().getReference();


        initView();
    }

    //[start init view]
    private void initView() {

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = etHeight.getText().toString().trim();

                //realtime
                String userID = mAuth.getCurrentUser().getUid();
                DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("height");

                currentUser.setValue(height);
                Toast.makeText(HeightQActivity.this, "successful", Toast.LENGTH_SHORT).show();

                startActivity( new Intent(HeightQActivity.this ,ShowInfoActivity.class));
            }
        });
    }//[end init view]
}
