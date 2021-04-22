package com.example.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// I am not even close to done with this. I've done a lot of reading and I'm going to attempt to try this out and see if I can get it to work with firebase storing data and also user authentication
public class FirebaseStorage {
    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth uAuth;
    @Override
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        uAuth = FirebaseAuth.getInstance();
        //initializes the firebase auth
    }

}
