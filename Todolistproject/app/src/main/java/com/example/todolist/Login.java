package com.example.todolist;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText lEmail, lPass;
    Button Loginbn, Regbn;
    FirebaseAuth lauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lEmail = findViewById(R.id.lemail);
        lPass = findViewById(R.id.lpass);
        Regbn = findViewById(R.id.toregpg);
        Loginbn = findViewById(R.id.loginb);
        lauth = FirebaseAuth.getInstance();
        
        
        Loginbn.setOnClickListener(new View.OnClickListener() {
            //When the log in button is clicked, it checks to see if user has put in both email and password of correct length:
                //If they haven't done so it displays the correct error message
                    //If everything is correct, the user is logged in with firebase and it checks to see if email and password match account info on firebase console. 
            @Override
            public void onClick(View v) {
                String email = lEmail.getText().toString().trim();
                String password = lPass.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    //displays error message if the user does not input email
                    lEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    //displays error message if the user does not input password
                    lPass.setError("Password is required");
                    return;
                }
                if(password.length() < 6) {
                    //displays error message if the user does not input password with 6 + characters
                    lPass.setError("Password must be at least 6 characters");
                }
                // User authentication!
                lauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // If everything is correct, then the user will be able to login
                            Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            //If an error occurs then user will get the error message
                            Toast.makeText(Login.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Regbn.setOnClickListener(new View.OnClickListener() {
            //If register button is clicked, it will go to the register page and run the activity.
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),Register.class));
            }
        });
    }
}