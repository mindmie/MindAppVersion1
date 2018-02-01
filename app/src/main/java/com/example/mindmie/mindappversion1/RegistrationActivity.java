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

public class RegistrationActivity extends AppCompatActivity {

    //view
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;

    //
    private ProgressDialog progressDialog;

    //authen
    private FirebaseAuth mAuth;
    //realtime db
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //view
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        progressDialog = new ProgressDialog(this);

        //Authen
        mAuth = FirebaseAuth.getInstance();
        //realtime db
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        initView();
    }

    //[start initView]
    private void initView() {
        // btn registration
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( emailValidation() && passwordValidation() && confirmPasswordValidation()){ //if it was validated , it can create new user

                    createNewUser();
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));

                }
                else{
                    Toast.makeText(RegistrationActivity.this, "create user is failed", Toast.LENGTH_SHORT).show();
                }
            }



            //[start  create user] this point coding create user function
            private void createNewUser() {
                String emailUser = etEmail.getText().toString().trim();
                String passwordUser = etPassword.getText().toString().trim();

                progressDialog.setMessage("Creating ...");
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(emailUser,passwordUser)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if( task.isSuccessful()){
                                    progressDialog.dismiss();
                                    startActivity( new Intent( RegistrationActivity.this, MainActivity.class));
                                    Toast.makeText(RegistrationActivity.this, " create a new account is successful", Toast.LENGTH_SHORT).show();
                                }
                                else if (task.getException() instanceof FirebaseAuthActionCodeException) {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegistrationActivity.this, "this account has used , please try again", Toast.LENGTH_SHORT).show();
                                    }
//                                    else
//                                    {
//                                        progressDialog.dismiss();
//                                        Toast.makeText(RegistrationActivity.this, "create a new account is failed", Toast.LENGTH_SHORT).show();
//                                    }


                            }
                        });





            }//[end create user]


        }); // [btn registration end]

        findViewById(R.id.btn_back_to_main).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(RegistrationActivity.this, MainActivity.class));
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
        });
        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                confirmPasswordValidation();
            }
        });// [end text watcher]
    }//[end initView]




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

    }

    private boolean confirmPasswordValidation(){
        boolean isConfirmPasswordValid = true;
        if ( etConfirmPassword != null && !etConfirmPassword.getText().toString().trim().matches(etPassword.getText().toString())){
            etConfirmPassword.setError("password is incorrect");
            isConfirmPasswordValid = false;
        }
        else if(etConfirmPassword.getText().toString().length() == 0 ){
            etConfirmPassword.setError("please fill this");
            isConfirmPasswordValid = false;
        }
        return isConfirmPasswordValid;

    }

    //[end validation]






}
