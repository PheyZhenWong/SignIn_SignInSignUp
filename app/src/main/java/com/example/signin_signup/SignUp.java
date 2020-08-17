package com.example.signin_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    private TextView backfirst;
    private EditText su_name,su_date,su_phone,su_email,su_pass,su_cpass;
    private Button su;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth= FirebaseAuth.getInstance();

        su_name=(EditText)findViewById(R.id.su_name);
        su_date=(EditText)findViewById(R.id.su_date);
        su_phone=(EditText)findViewById(R.id.su_phone);
        su_email=(EditText)findViewById(R.id.su_email);
        su_pass=(EditText)findViewById(R.id.su_pass);
        su_cpass=(EditText)findViewById(R.id.su_cpass);
        backfirst=(TextView)findViewById(R.id.backfst);
        su=(Button)findViewById(R.id.sign_up);

        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = su_name.getText().toString().trim();
                String date = su_date.getText().toString().trim();
                String phone = su_phone.getText().toString().trim();
                String email = su_email.getText().toString().trim();
                String password = su_pass.getText().toString().trim();
                String cpassword = su_cpass.getText().toString().trim();


                if (name.equals("") || date.equals("") || phone.equals("") || email.equals(("")) || password.equals("") || cpassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill in all the blank", Toast.LENGTH_SHORT).show();

                } else if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password length must more than 6 number.", Toast.LENGTH_SHORT).show();
                }else if (password.equals(cpassword)) {
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                       if(task.isSuccessful()){
                                           Toast.makeText(getApplicationContext(), "Sign Up successfully", Toast.LENGTH_SHORT).show();
                                           Intent moveToSignIn = new Intent(getApplicationContext(),SignIn.class);
                                           startActivity(moveToSignIn);
                                       }else{
                                           Toast.makeText(getApplicationContext(),"Sign Up Failed",Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               });
                    } else {
                        Toast.makeText(getApplicationContext(),"Confirm password does not match the password",Toast.LENGTH_SHORT).show();
                    }
                }


        });

        su_date.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    su_date.setText(current);
                    su_date.setSelection(sel < current.length() ? sel : current.length());



                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
        backfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirst();
            }
        });
    }
    public void openFirst() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


}