package com.vangcc19135.restaurantManagementSystem.UserExperience.detailbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vangcc19135.RestaurantManagementSystem.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NoteActivity extends AppCompatActivity {
    private EditText mEditText;
    private Button mSaveButton, back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mEditText = findViewById(R.id.editText);
        mSaveButton = findViewById(R.id.saveButton);
        back = findViewById(R.id.BackNote);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mEditText.getText().toString();
                saveToFile(text);
            }
        });

        // Load saved data from file and display in EditText
        String savedText = loadFromFile();
        mEditText.setText(savedText);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteActivity.this, DetailTableActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveToFile(String text) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput("data.txt", MODE_PRIVATE);
            fos.write(text.getBytes());
            Toast.makeText(this, "Data saved to file", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private String loadFromFile() {
        FileInputStream fis = null;
        String savedText = "";

        try {
            fis = openFileInput("data.txt");
            int length = fis.available();
            byte[] buffer = new byte[length];
            fis.read(buffer);
            savedText = new String(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return savedText;
    }
}
