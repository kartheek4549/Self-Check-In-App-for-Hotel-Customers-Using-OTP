package com.infinityandroid.hotelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class BookActivity extends AppCompatActivity {
    EditText phone, otp;
    Button btngenOTP, btnverify;
    private Button btnLogOut;
    FirebaseAuth mAuth;
    String verificationID;
    ProgressBar bar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        phone = findViewById(R.id.phone);
        otp  = findViewById(R.id.otp);
        btngenOTP = findViewById(R.id.btngenerateOTP);
        btnverify = findViewById(R.id.btnverifyOTP);
        btnLogOut = findViewById(R.id.btnLogOut);
        mAuth = FirebaseAuth.getInstance();
        bar = findViewById(R.id.bar);
        btnLogOut.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(BookActivity.this, LoginActivity.class));
        });
        btngenOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (TextUtils.isEmpty(phone.getText().toString()))
                {
                    Toast.makeText(BookActivity.this, "Enter valid phone No.", Toast.LENGTH_SHORT).show();
                }
                String number = phone.getText().toString();
                bar.setVisibility(View.VISIBLE);
                sendverificationcode(number);
            }
        });
        btnverify.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v)
            {
                if (TextUtils.isEmpty(otp.getText().toString()))
                {
                    Toast.makeText(BookActivity.this, "Wrong OTP.", Toast.LENGTH_SHORT).show();
                }
                else
                    verifycode(otp.getText().toString());
            }
        });

    }

    private void sendverificationcode(String phoneNumber)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
        {
            final  String code = credential.getSmsCode();
            if(code!=null)
            {
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e)
        {
            Toast.makeText(BookActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(s, token);
            verificationID = s;
            Toast.makeText(BookActivity.this, "code sent", Toast.LENGTH_SHORT).show();
            btnverify.setEnabled(true);
            bar.setVisibility(View.INVISIBLE);
        }
    };



    private void verifycode(String Code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID,Code);
        signinbyCredentails(credential);
    }

    private void signinbyCredentails(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(BookActivity.this, "Room has been Booked Successfully", Toast.LENGTH_SHORT).show();


                        }

                    }
                });
    }

        }


