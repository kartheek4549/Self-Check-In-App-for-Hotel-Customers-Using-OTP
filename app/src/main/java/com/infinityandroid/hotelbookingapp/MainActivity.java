package com.infinityandroid.hotelbookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.Calendar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{
    private Button button1;
    FirebaseAuth mAuth;
    float x1, x2, y1, y2;
    private Spinner spinner,spinner2;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        mAuth = FirebaseAuth.getInstance();
        button1 = (Button) findViewById(R.id.button1);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        String[] numbering = getResources().getStringArray(R.array.numbers);
        String[] numberings = getResources().getStringArray(R.array.rooms);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,numbering);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,numberings);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);
        button1.setOnClickListener(new View.OnClickListener() {
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
                    Intent i = new Intent(MainActivity.this, SwipeLeftActivity.class);
                    startActivity(i);
                }
                else if(x1 > x2)
                {
                    Intent i = new Intent(MainActivity.this, SwipeLeftActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}