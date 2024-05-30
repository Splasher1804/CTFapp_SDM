package com.example.ctfapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class DynamicAnalysisActivity extends AppCompatActivity {

    private static final String TAG = "DynamicAnalysisActivity";
    private static final String SECRET_CODE = "CTF2024"; // Secret code for the challenge

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_analysis);

        EditText inputField = findViewById(R.id.input_field);
        Button checkButton = findViewById(R.id.check_button);
        TextView flagTextView = findViewById(R.id.flag_text_view);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = inputField.getText().toString();
                Log.d(TAG, "User input: " + userInput); // Log user input

                if (SECRET_CODE.equals(userInput)) {
                    String creditCardInfo = generateFakeCreditCardInfo();
                    String flag = "FLAG{" + creditCardInfo + "}";
                    Log.d(TAG, "Generated flag: " + flag); // Log the generated flag
                    flagTextView.setText(flag);
                    flagTextView.setVisibility(View.VISIBLE);
                } else {
                    flagTextView.setText("Incorrect code. Try again.");
                    flagTextView.setVisibility(View.VISIBLE);
                    Log.d(TAG, "Incorrect code entered."); // Log incorrect attempt
                }
            }
        });
    }

    private String generateFakeCreditCardInfo() {
        Random random = new Random();
        String cardNumber = String.format("%04d %04d %04d %04d",
                random.nextInt(10000), random.nextInt(10000), random.nextInt(10000), random.nextInt(10000));
        String expiryDate = String.format("%02d/%02d", random.nextInt(12) + 1, random.nextInt(10) + 24);
        String cvv = String.format("%03d", random.nextInt(1000));
        String creditCardInfo = "CC: " + cardNumber + " EXP: " + expiryDate + " CVV: " + cvv;
        Log.d(TAG, "Generated credit card info: " + creditCardInfo); // Log generated credit card info
        return creditCardInfo;
    }
}
