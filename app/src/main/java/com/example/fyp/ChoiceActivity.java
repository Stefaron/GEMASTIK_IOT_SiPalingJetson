package com.example.fyp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import org.jetbrains.annotations.Nullable;

public class ChoiceActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choice);
        Audio audio = new Audio(this, "en", "GB");

        Button button1 = this.findViewById(R.id.Detection);
        Button button2 = this.findViewById(R.id.DnA);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View it) {
                audio.generateAudio("Detection has started.");
                Intent intent = new Intent(ChoiceActivity.this, ObjectDetectionActivity.class);
                ChoiceActivity.this.startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View it) {
                audio.generateAudio("Distance and angle easurement has started.");
                Intent intent = new Intent(ChoiceActivity.this, DnA_Activity.class);
                ChoiceActivity.this.startActivity(intent);
            }
        });
    }
}
