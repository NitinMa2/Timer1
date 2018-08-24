package com.example.nitin.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    SeekBar seekBar;
    Boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        seekBar.setProgress(0);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("Start");
        counterIsActive = false;
    }

    public void buttonClick(View view){
        if (counterIsActive){
            textView.setText("00:00");
            seekBar.setProgress(0);
            seekBar.setEnabled(true);
            countDownTimer.cancel();
            goButton.setText("Start");
            counterIsActive = false;
        }
        else {
            counterIsActive = true;
            seekBar.setEnabled(false);
            goButton.setText("Stop");
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    textView.setText("00:00");
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int progress){
        int min = progress/60;
        int sec = progress - (min*60);
        String secondString = Integer.toString(sec);
        int foo = Integer.parseInt(secondString);
        if(foo/10 == 0){
            secondString = "0" + foo%10;
        }
        textView.setText(Integer.toString(min) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        goButton = findViewById(R.id.startButton);

        seekBar.setMax(600);//seconds
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
