package com.example.fyp;

import android.content.Context;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabelerOptionsBase;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Classifier {
    private ImageLabeler labeler;
    private String labelmap;

    public final void load_model( Context context) {
        ImageLabelerOptions img = (new ImageLabelerOptions.Builder()).setConfidenceThreshold(0.7F).build();
        this.labeler = ImageLabeling.getClient(img);
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getApplicationContext().getAssets().open("labelmap.txt"), StandardCharsets.UTF_8));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                this.labelmap = mLine;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void classify( InputImage input, String callback) {
       /* var result: String = ""
        var temp: String = ""

        labeler.process(input)
                .addOnSuccessListener { labels ->
            for (label in labels) {
                val text = label.text
                val confidence = label.confidence
                val index = label.index

                if(labelmap.contains(text))
                {
                    result += text + "\n"
                }
            }
            temp = result
            result = ""

            callback(temp)
        }
                .addOnFailureListener { e ->

        }*/
    }
}
