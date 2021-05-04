/* Resources used for *most* of this code, with a few adjustments based on my application
https://youtu.be/tbh9YaWPKKs
https://youtu.be/TwHmrZxiPA8*/

package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText rName, rEmail, rPass;
    Button Registerbtn, loginpg;
    FirebaseAuth rauth;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //This is the screen that shows when the app is launched that allows users to register for an account.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rName = findViewById(R.id.rname);
        rEmail = findViewById(R.id.remail);
        rPass = findViewById(R.id.rpass);
        Registerbtn = findViewById(R.id.racc);
        loginpg = findViewById(R.id.tologinpg);
        
        rauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        
        if(rauth.getCurrentUser() != null){
            //If the user is already logged in, they will be sent straight to the mainactivity aka the home screen
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When the register button is clicked, it checks to see if user has put in name, email, and password of correct length:
                //If they haven't done so it displays the correct error message
                    //Then it creates an account with email and password given and updates on the firebase user console
                String email = rEmail.getText().toString().trim();
                String password = rPass.getText().toString().trim();
                String name = rName.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    //displays error message if the user does not input email
                    rEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    //displays error message if the user does not input password
                    rPass.setError("Password is required");
                    return;
                }
                if(password.length() < 6) {
                    //displays error message if the user does not input password with 6 + characters
                    rPass.setError("Password must be at least 6 characters");
                }

                // registering the user's account with firebase

                rauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //If the account is created successfully, the user will be sent to mainactivity (for my app that will be the home screen)
                            Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();
                            userID = rauth.getCurrentUser().getUid(); //gets the userid of the current user so I can store info in their account
                            DocumentReference documentref = fstore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<String,Object>();
                            user.put("Name",name);
                            user.put("Email", email); //adds the email and name to the firebase cloud db
                            documentref.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for" + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            //If an error occurs then user will get the error message
                            Toast.makeText(Register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        
        loginpg.setOnClickListener(new View.OnClickListener() {
            //If login button is clicked, it will go to the login page and run the activity.
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),Login.class));
            }
        });
    }
}