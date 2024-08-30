package com.example.textconverter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int IMAGE_PICKER_REQUEST_CODE = 1001;

    Button cambtn, btnclear, copybtn;
    EditText recogtext;
    Uri imageuri;
    TextRecognizer textRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cambtn = findViewById(R.id.cambtn);
        btnclear = findViewById(R.id.btnclear);
        copybtn = findViewById(R.id.copybtn);
        recogtext = findViewById(R.id.recogtext);

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        cambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(MainActivity.this)
                        .crop()                // Enables cropping (Optional)
                        .compress(1024)        // Compresses image to be less than 1MB (Optional)
                        .maxResultSize(1080, 1080)  // Sets max resolution to 1080x1080 (Optional)
                        .start(IMAGE_PICKER_REQUEST_CODE); // Pass the request code here
            }
        });

        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogtext.setText(""); // Clears the text in the EditText
                Toast.makeText(MainActivity.this, "Text cleared", Toast.LENGTH_SHORT).show();
            }
        });

        copybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToCopy = recogtext.getText().toString();
                if (!textToCopy.isEmpty()) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Recognized Text", textToCopy);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(MainActivity.this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No text to copy", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Toast.makeText(this, "Image is selected", Toast.LENGTH_SHORT).show();
            imageuri = data.getData();
            recognizetxt();
        } else {
            Toast.makeText(this, "Image is not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void recognizetxt() {
        try {
            InputImage inputImage = InputImage.fromFilePath(MainActivity.this, imageuri);

            Task<Text> result = textRecognizer.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {
                            String recognizeText = text.getText();
                            recogtext.setText(recognizeText);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
