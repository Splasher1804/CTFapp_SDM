package com.example.ctfapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TcpChallengeActivity extends AppCompatActivity {

    private static final List<String> WORDS = Arrays.asList("apple", "grape", "peach", "lemon", "berry", "melon", "mango", "plum", "olive", "cherry");
    private static final String FLAG_PATTERN = "flag{%s}";

    private EditText inputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_challenge);

        inputEditText = findViewById(R.id.inputEditText);
        Button validateButton = findViewById(R.id.validateButton);

        validateButton.setOnClickListener(this::validateInput);
    }

    private void validateInput(View view) {
        String inputText = inputEditText.getText().toString().trim();

        if (isValidFlag(inputText)) {
            Toast.makeText(this, "Correct! Moving to the next level...", Toast.LENGTH_SHORT).show();
            // Lansează următorul nivel aici
            // Intent intent = new Intent(TcpChallengeActivity.this, NextLevelActivity.class);
            // startActivity(intent);
        } else {
            Toast.makeText(this, "Incorrect flag. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidFlag(String inputText) {
        if (!inputText.startsWith("flag{") || !inputText.endsWith("}")) {
            return false;
        }

        String content = inputText.substring(5, inputText.length() - 1);
        if (content.length() != 5) {
            return false;
        }

        String part1 = content.substring(0, 5);
        //String part2 = content.substring(5, 10);
        //String part3 = content.substring(10, 15);

        return WORDS.contains(part1); //&& WORDS.contains(part2) && WORDS.contains(part3);
    }

    // Funcție pentru generarea unei steag de exemplu
    private String generateExampleFlag() {
        Random random = new Random();
        String word1 = WORDS.get(random.nextInt(WORDS.size()));
        //String word2 = WORDS.get(random.nextInt(WORDS.size()));
        //String word3 = WORDS.get(random.nextInt(WORDS.size()));
        return String.format(FLAG_PATTERN, word1); //, word2, word3
    }
}