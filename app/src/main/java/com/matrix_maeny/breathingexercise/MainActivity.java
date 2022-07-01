package com.matrix_maeny.breathingexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SettingsDialog.SettingsDialogListener {

    AppCompatButton startBtn;
    TextView breathTextView, breathCountTextView;
    ImageView settingsView;

    int count = 0, time = 6;

    volatile boolean flag = true;
    boolean start = true;

    final Handler handler = new Handler();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        startBtn = findViewById(R.id.startBtn);
        breathTextView = findViewById(R.id.breathTextView);
        settingsView = findViewById(R.id.settingsView);
        breathCountTextView = findViewById(R.id.breathCountTextView);

        startBtn.setOnClickListener(v -> {
            if (start) {
                flag = true;
                startCounting();
                startBtn.setText("STOP");
            } else {
                flag = false;
                breathCountTextView.setText("0");
                startBtn.setText("START");

            }
            start = !start;

        });

        settingsView.setOnClickListener(v -> {
            SettingsDialog dialog = new SettingsDialog();
            dialog.show(getSupportFragmentManager(),"Settings dialog");
        });
    }

    private void startCounting() {
        count = 0;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (flag) {
                    if (count < time) {
                        count++;
                    } else {
                        count = 0;
                        if (breathTextView.getText().toString().equals(getString(R.string.inhale))) {
                            breathTextView.setText(getString(R.string.exhale));
                        } else {
                            breathTextView.setText(getString(R.string.inhale));

                        }
                    }
                    breathCountTextView.setText(String.valueOf(count));

                    handler.postDelayed(this, 1000);

                }
            }
        }, 1000);
    }

    @Override
    public void getTime(int time) {
        this.time = time;
        Toast.makeText(this, ""+time, Toast.LENGTH_SHORT).show();
    }
}