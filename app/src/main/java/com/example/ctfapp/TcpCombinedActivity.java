package com.example.ctfapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class TcpCombinedActivity extends AppCompatActivity {

    private static final String TAG = "TcpCombinedActivity";

    private EditText flagInput;
    private Button validateButton;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter serverOutput;
    private BufferedReader serverInput;
    private PrintWriter clientOutput;
    private BufferedReader clientInput;

    private Handler handler = new Handler(Looper.getMainLooper());
   // private static final String[] WORDS = {"apple", "grape", "peach", "lemon", "berry",
   //         "melon", "mango", "plum", "olive", "cherry"};
   private static final String[] WORDS = {
           "1f3870be274f6c49b3e31a0c6728957f",
           "b781cbb29054db12f88f08c6e161c199",
           "889560d93572d538078ce1578567b91a",
           "3f24e567591e9cbab2a7d2f1f748a1d4",
           "5076e33378db3b35c88d9189851ed3bf",
           "3a2cf27458c9aa3e358f8fc0f002bff6",
           "aa00faf97d042c13a59da4d27eb32358",
           "3e042037287d6871eec3dbd48556b0b4",
           "f431b0eea3c08186ed101e588bfb3a2f",
           "c7a4476fc64b75ead800da9ea2b7d072"
   };
    static Random rand = new Random();
    static int rand_index = rand.nextInt(WORDS.length);

    private static final String CORRECT_WORD = WORDS[rand_index]; // Cuvântul corect

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_combined);

        flagInput = findViewById(R.id.flagInput);
        validateButton = findViewById(R.id.validateButton);

        validateButton.setOnClickListener(v -> validateFlag());

        new Thread(new ServerRunnable()).start();
        new Thread(new ClientRunnable()).start();
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

    private void validateFlag() {
        String inputText = flagInput.getText().toString().trim();
        if (inputText.startsWith("flag{") && inputText.endsWith("}")) {
            String word = inputText.substring(5, inputText.length() - 1);
            String md5word = md5(word);
            if (md5word.equals(CORRECT_WORD)) {
                Toast.makeText(this, "Correct flag! Proceeding to next activity.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TcpCombinedActivity.this, DynamicAnalysisActivity.class);
                startActivity(intent);
                return;
            }
        }
        Toast.makeText(this, "Incorrect flag. Try again.", Toast.LENGTH_SHORT).show();
    }

    private class ServerRunnable implements Runnable {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(12345);
                handler.post(() -> Toast.makeText(TcpCombinedActivity.this, "Server started on port 12345", Toast.LENGTH_SHORT).show());

                Socket socket = serverSocket.accept();
                handler.post(() -> Toast.makeText(TcpCombinedActivity.this, "Client connected", Toast.LENGTH_SHORT).show());

                serverOutput = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String message;
                Random random = new Random();
                while ((message = serverInput.readLine()) != null) {
                    Log.d(TAG, "Server received: " + message);
                    String response = WORDS[random.nextInt(WORDS.length)];
                    serverOutput.println(response);
                    Log.d(TAG, "Server sent: " + response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> Toast.makeText(TcpCombinedActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }
    }

    private String md5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private class ClientRunnable implements Runnable {
        @Override
        public void run() {
            try {
                // Așteaptă puțin pentru a permite serverului să se pornească
                Thread.sleep(2000);

                clientSocket = new Socket("10.0.2.16", 12345); // Use 10.0.2.2 to refer to localhost from the emulator
                clientOutput = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                handler.post(() -> Toast.makeText(TcpCombinedActivity.this, "Connected to server", Toast.LENGTH_SHORT).show());

                String message;
                Random random = new Random();
                while (true) {
                    // Clientul trimite un cuvânt random
                    String randomWord = WORDS[random.nextInt(WORDS.length)];
                    clientOutput.println(randomWord);
                    Log.d(TAG, "Client sent: " + randomWord);

                    // Clientul primește răspuns de la server
                    if ((message = clientInput.readLine()) != null) {
                        Log.d(TAG, "Client received: " + message);
                    }

                    // Așteaptă puțin înainte de a trimite următorul mesaj
                    Thread.sleep(5000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> Toast.makeText(TcpCombinedActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }
    }
}