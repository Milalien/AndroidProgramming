package com.example.android_harjoitukset;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private Button startButton;
    private Button gameButton;
    private View helloTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        helloTextView = findViewById(R.id.hello);
        helloTextView.setVisibility(View.VISIBLE);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);

            }
        });
        gameButton = findViewById(R.id.gameButton);
        gameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });
    }

    private void handleOnClickEvents(View v) {
        if (v.getId() == R.id.startButton) {
            Log.i(TAG, "User clicked the Start-button");
            if (helloTextView.getVisibility() == View.VISIBLE) {
                helloTextView.setVisibility(View.INVISIBLE);
            } else if (helloTextView.getVisibility() == View.INVISIBLE) {
                helloTextView.setVisibility(View.VISIBLE);

            }
        } else if (v.getId() == R.id.gameButton) {
            Log.i(TAG, "User clicked the Game-button");
            Intent i = new Intent(this, GameActivity.class);

            startActivity(i);
        }

    }
}