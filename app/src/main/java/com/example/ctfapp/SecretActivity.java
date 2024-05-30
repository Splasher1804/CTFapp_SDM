// SecretActivity.java
package com.example.ctfapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecretActivity extends AppCompatActivity {
    private static final String DYNAMIC_FLAG = "FLAG{dynamic_flag_value}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);

        Button revealButton = findViewById(R.id.reveal_button);
        revealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealFlag();
            }
        });
    }

    private void revealFlag() {
        TextView flagTextView = findViewById(R.id.flag_text_view);
        flagTextView.setText(DYNAMIC_FLAG);
        flagTextView.setVisibility(View.VISIBLE);
    }
}
