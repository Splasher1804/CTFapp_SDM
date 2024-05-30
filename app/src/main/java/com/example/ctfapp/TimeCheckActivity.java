package com.example.ctfapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class TimeCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_check);

        // Referințe la EditText și Button
        EditText editText = findViewById(R.id.editText);
        Button validateButton = findViewById(R.id.validateButton);

        // Setează textul inițial al EditText-ului
        editText.setHint("Enter text here");

        // Setează un OnClickListener pe buton
        validateButton.setOnClickListener(v -> {
            String inputText = editText.getText().toString();
            if (isCurrentTime(inputText)) {
               Toast.makeText(this, "Textul este corect!", Toast.LENGTH_SHORT).show();
                // Continuați cu alte acțiuni aici dacă textul este corect
                Intent intent = new Intent(TimeCheckActivity.this, TcpCombinedActivity.class);
                startActivity(intent);
                finish();

            } else {
               Toast.makeText(this, "Textul introdus este incorect.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isCurrentTime(String inputText) {
        Calendar calendar = Calendar.getInstance();
        String currentTime = DateFormat.format("HH:mm", calendar).toString();
        //Toast.makeText(this ,currentTime, Toast.LENGTH_SHORT).show();
        return inputText.equals(currentTime);
    }
}