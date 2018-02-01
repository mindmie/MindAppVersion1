package com.example.mindmie.mindappversion1;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NameQActivity extends AppCompatActivity {


    //view
    private EditText etName;

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
        setContentView(R.layout.activity_name_q);

        //view
        etName = findViewById(R.id.et_name);
        progressDialog = new ProgressDialog(this);

        //Auth
        mAuth = FirebaseAuth.getInstance();

        //realtime db
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //firestore
        mFirestore = FirebaseFirestore.getInstance();


        //intent
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null){
//            String userID = bundle.getString("userID");
//        }
//

        initView();


    }

    //[start init view]
    private void initView() {

        //[start btn save]
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                String name = etName.getText().toString().trim();

                //realtime
                String userID = mAuth.getCurrentUser().getUid();
                DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("name");

                // when we have many adding data , add set of data
                //Map addData = new HashMap();
                //addData.put("name", name);
                //currentUser.setValue(addData);

                currentUser.setValue(name);
                Toast.makeText(NameQActivity.this, "successful", Toast.LENGTH_SHORT).show();

                startActivity( new Intent(NameQActivity.this , AgeQActivity.class));


                //firestore
//                String name = et_name.getText().toString();
//
//                Map<String , String > userMap = new HashMap<>();
//                userMap.put("name", name);
//
//                mFirestore.collection("users").add(userMap)
//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//
//                                Toast.makeText(NameQActivity.this, "add to firestore", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                        String error = e.getMessage();
//                        Toast.makeText(NameQActivity.this, "error :" + error, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//                startActivity( new Intent(NameQActivity.this,ShowInfoActivity.class));
            }
        }); //[end btn save]

    }//[end init view]


}
