package com.example.fyp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton button = this.findViewById(R.id.start);
        final Audio sp = new Audio(this, "en", "GB");
        sp.generateAudio("Welcome to BlindVision.Tap on the screen to start.");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View it) {
                sp.generateAudio("Choose your requirement");
                Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
