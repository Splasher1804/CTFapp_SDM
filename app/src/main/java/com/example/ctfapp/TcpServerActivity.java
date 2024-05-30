package com.example.ctfapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServerActivity extends AppCompatActivity {

    private TextView messageView;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;

    private static final String[] WORDS = {"apple", "grape", "peach", "lemon", "berry",
            "melon", "mango", "plum", "olive", "cherry"};

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_server);

        messageView = findViewById(R.id.messageView);

        new Thread(new ServerRunnable()).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ServerRunnable implements Runnable {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(12345);
                handler.post(() -> Toast.makeText(TcpServerActivity.this, "Server started on port 12345", Toast.LENGTH_SHORT).show());

                clientSocket = serverSocket.accept();
                handler.post(() -> Toast.makeText(TcpServerActivity.this, "Client connected", Toast.LENGTH_SHORT).show());

                output = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Lansează TcpClientActivity după ce clientul se conectează
                handler.post(() -> {
                    Intent intent = new Intent(TcpServerActivity.this, TcpClientActivity.class);
                    startActivity(intent);
                });

                String message;
                while ((message = input.readLine()) != null) {
                    String finalMessage = message;
                    handler.post(() -> messageView.append("Client: " + finalMessage + "\n"));
                    String response = WORDS[(int) (Math.random() * WORDS.length)];
                    output.println(response);
                    handler.post(() -> messageView.append("Server: " + response + "\n"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> Toast.makeText(TcpServerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } finally {
                try {
                    if (clientSocket != null) clientSocket.close();
                    if (serverSocket != null) serverSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}