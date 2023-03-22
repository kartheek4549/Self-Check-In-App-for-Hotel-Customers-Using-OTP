package com.infinityandroid.hotelbookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SwipeLeftActivity extends AppCompatActivity {
    private Button button2;
    FirebaseAuth mAuth;
    float x1, x2, y1, y2;
    private Spinner spinner1,spinner3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_left);
        button2 = (Button) findViewById(R.id.button2);
        spinner1 = findViewById(R.id.spinner1);
        spinner3 = findViewById(R.id.spinner3);
        String[] numbering = getResources().getStringArray(R.array.numbers);
        String[] numberings = getResources().getStringArray(R.array.rooms);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,numbering);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,numberings);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter1);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBookActivity();
            }
        });
    }
    public void openBookActivity() {
        Intent intent = new Intent(this, BookActivity.class);
        startActivity(intent);
    }
    public boolean onTouchEvent (MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1= touchEvent.getX();
                y1= touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2= touchEvent.getX();
                y2= touchEvent.getY();
                if( x1 < x2) {
                    Intent i = new Intent(SwipeLeftActivity.this, SwipeLeft2.class);
                    startActivity(i);
                }
                else if(x1 > x2)
                {
                    Intent i = new Intent(SwipeLeftActivity.this, SwipeLeft2.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

    }
