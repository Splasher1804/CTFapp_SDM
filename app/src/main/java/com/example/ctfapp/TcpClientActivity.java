package com.example.ctfapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpClientActivity extends AppCompatActivity {

    private EditText inputMessage;
    private TextView messageView;
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_client);

        inputMessage = findViewById(R.id.inputMessage);
        messageView = findViewById(R.id.messageView);
        Button sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(this::sendMessage);

        new Thread(new ClientRunnable()).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (socket != null) socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(View view) {
        String message = inputMessage.getText().toString();
        if (message.length() <= 5) {
            new Thread(() -> {
                output.println(message);
                handler.post(() -> messageView.append("Client: " + message + "\n"));
                inputMessage.setText("");
            }).start();
        } else {
            Toast.makeText(this, "Message too long. Max 5 characters.", Toast.LENGTH_SHORT).show();
        }
    }

    private class ClientRunnable implements Runnable {
        @Override
        public void run() {
            try {
                socket = new Socket("10.0.2.2", 12345); // Use 10.0.2.2 to refer to localhost from the emulator
                output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                handler.post(() -> Toast.makeText(TcpClientActivity.this, "Connected to server", Toast.LENGTH_SHORT).show());

                // Lansează TcpChallengeActivity după ce clientul se conectează
                handler.post(() -> {
                    Intent intent = new Intent(TcpClientActivity.this, TcpChallengeActivity.class);
                    startActivity(intent);
                });

                String message;
                while ((message = input.readLine()) != null) {
                    String finalMessage = message;
                    handler.post(() -> messageView.append("Server: " + finalMessage + "\n"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> Toast.makeText(TcpClientActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}