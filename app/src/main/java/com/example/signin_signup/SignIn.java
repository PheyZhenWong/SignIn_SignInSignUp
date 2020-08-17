package com.example.signin_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private TextView gofirst;
    private EditText si_name, si_email, si_password;
    private Button si;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        si_name = (EditText) findViewById(R.id.si_name);
        si_email = (EditText) findViewById(R.id.si_email);
        si_password = (EditText) findViewById(R.id.si_password);
        gofirst = (TextView) findViewById(R.id.backmain);
        si = (Button) findViewById(R.id.sign_in);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = si_name.getText().toString().trim();
                String email = si_email.getText().toString().trim();
                String password = si_password.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Sign In successfully", Toast.LENGTH_SHORT).show();
                                    Intent moveHomepage = new Intent(SignIn.this, SignUp.class);
                                    startActivity(moveHomepage);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Sign In Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });

        gofirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
