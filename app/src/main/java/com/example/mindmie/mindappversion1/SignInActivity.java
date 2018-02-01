package com.example.mindmie.mindappversion1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;

    //
    private ProgressDialog progressDialog;

    //authen
    private FirebaseAuth mAuth;
    //realtime db
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //view
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        progressDialog = new ProgressDialog(this);

        //Authen
        mAuth = FirebaseAuth.getInstance();
        //realtime db
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initView();
    }

    //[start init view ]
    private void initView() {
        findViewById(R.id.btn_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( emailValidation() && passwordValidation() ){ //if it was validated , it can Sign in
                    SignInUser();

                }
                else{
                    Toast.makeText( SignInActivity.this, "create user is failed", Toast.LENGTH_SHORT).show();
                }

            }

            private void SignInUser() {
                final String emailUser = etEmail.getText().toString().trim();
                final String passwordUser = etPassword.getText().toString().trim();

                progressDialog.setMessage("Signing ...");
                progressDialog.show();

                mAuth.signInWithEmailAndPassword( emailUser , passwordUser)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();

                                    //real-time db
                                    addUser(emailUser,passwordUser);


                                    Toast.makeText(SignInActivity.this, "successful", Toast.LENGTH_SHORT).show();
                                    updateUI();



                                }
                                else if (task.getException() instanceof FirebaseAuthActionCodeException){
                                    progressDialog.dismiss();
                                    Toast.makeText(SignInActivity.this, "failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }




            //[start add user]
            private void addUser(String emailUser , String passwordUser) {

                String userID = mAuth.getCurrentUser().getUid();
                DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                Map addData = new HashMap();

                addData.put("email",emailUser);
                addData.put("password",passwordUser);
                currentUser.setValue(addData);


            }//[end add user user]

            //[start update ui]
            private void updateUI() {
                Intent i = new Intent( SignInActivity.this, NameQActivity.class);
                i.putExtra("userID",mAuth.getCurrentUser().getUid());
                startActivity(i);
            }//[end update ui]

        });

        findViewById(R.id.btn_back_to_main).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(SignInActivity.this, MainActivity.class));
            }
        });


        //[start text watcher]
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                emailValidation();

            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordValidation();

            }
        });// [end text watcher]
    } //[end init view ]


    //[start validation]
    private boolean emailValidation() {
        boolean isEmailValid = true;
        if (etEmail.getText().toString().length() == 0 || !etEmail.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            etEmail.setError("invalid email");
            isEmailValid = false;
        }
        else if(etEmail.getText().toString().length() == 0 ){
            etEmail.setError("please fill this");
            isEmailValid = false;
        }
        return isEmailValid;
    }

    private boolean passwordValidation (){
        boolean isPasswordValid = true;
        if ( etPassword != null && etPassword.getText().toString().length() < 6 ){
            etPassword.setError("must be at least 6 characters");
            isPasswordValid = false;
        }
        else if(etPassword.getText().toString().length() == 0 ){
            etEmail.setError("please fill this");
            isPasswordValid = false;
        }
        return  isPasswordValid;

    }//[end validation]



}

