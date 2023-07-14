package com.example.fyp;

import android.graphics.Rect;
import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Frame;
import com.otaliastudios.cameraview.FrameProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ObjectDetectionActivity extends AppCompatActivity {

    ObjectDetectorOptions options;
    ObjectDetector objectDetector;

    private CameraView cameraView;

    private FrameLayout parentLayout;

    private Audio audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);

        audio = new Audio(this, "en", "GB");
        cameraView = findViewById(R.id.cameraView);
        parentLayout = findViewById(R.id.parentLayout);
        options = new ObjectDetectorOptions.Builder()
                .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
                .enableClassification()
                .build();

        objectDetector = ObjectDetection.getClient(options);
        //        LocalModel localModel =
//                new LocalModel.Builder()
//                        .setAssetFilePath("ssd_mobilenet_v1_1_default_1.tflite")
//                        .build();

//        customObjectDetectorOptions =
//                new CustomObjectDetectorOptions.Builder(localModel)
//                        .setDetectorMode(CustomObjectDetectorOptions.STREAM_MODE)
//                        .enableClassification()
//                        .setClassificationConfidenceThreshold(0.5f)
//                        .setMaxPerObjectLabelCount(3)
//                        .build();

        setupCamera();
    }

    private void setupCamera() {
        this.cameraView.setLifecycleOwner(this);
        this.cameraView.addFrameProcessor(new FrameProcessor() {
            @Override
            public void process(@NonNull @NotNull Frame frame) {
                processImage(getInputImageFromFrame(frame));
            }
        });
    }

    private InputImage getInputImageFromFrame(Frame frame) {
        byte[] data = frame.getData();
        return InputImage.fromByteArray(data, frame.getSize().getWidth(), frame.getSize().getHeight(), frame.getRotation(), InputImage.IMAGE_FORMAT_NV21);
    }

    private void processImage(InputImage inputImage) {
        objectDetector.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<DetectedObject>>() {
                    @Override
                    public void onSuccess(List<DetectedObject> detectedObjects) {
                        processResults(detectedObjects);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                    }
                });
    }

    private void processResults(List<DetectedObject> detectedObjects) {
        for (DetectedObject i : detectedObjects) {
            if (this.parentLayout.getChildCount() > 1) {
                this.parentLayout.removeViewAt(1);
            }
            Rect boundingBox = i.getBoundingBox();
            //Log.d("MainActivity", "processResults: " + i.getLabels().toString());
            String text;
            if (i.getLabels().size() != 0) {
                text = i.getLabels().get(0).getText();
            } else {
                text = "Undefined";
            }
            Draw element = new Draw(this, boundingBox, text);
            this.parentLayout.addView(element);
            runOnUiThread(() -> {
                this.audio.generateAudio(text);
            });
        }
    }
}
