package com.infinityandroid.hotelbookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class SwipeLeft3 extends AppCompatActivity {
    private Button button3;
    FirebaseAuth mAuth;
    float x1, x2, y1, y2;
    private Spinner spinner6,spinner7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_left3);
        button3 = (Button) findViewById(R.id.button3);
        spinner6 = findViewById(R.id.spinner6);
        spinner7 = findViewById(R.id.spinner7);
        String[] numbering = getResources().getStringArray(R.array.numbers);
        String[] numberings = getResources().getStringArray(R.array.rooms);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,numbering);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,numberings);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner7.setAdapter(adapter1);
        button3.setOnClickListener(new View.OnClickListener() {
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
                    Intent i = new Intent(SwipeLeft3.this, SwipeLeftActivity.class);
                    startActivity(i);
                }
                else if(x1 > x2)
                {
                    Intent i = new Intent(SwipeLeft3.this, SwipeLeftActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}