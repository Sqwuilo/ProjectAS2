package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.R;

public class NotesTakerActivity extends AppCompatActivity {

    EditText editText_Title;
    EditText editText_Notes;
    ImageView imageView_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);


        editText_Title = findViewById(R.id.editText_Title);
        editText_Notes = findViewById(R.id.editText_Notes);
        imageView_save = findViewById(R.id.imageView_save);
    }
}