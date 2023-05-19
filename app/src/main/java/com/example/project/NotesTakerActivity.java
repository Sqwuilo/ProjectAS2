package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.project.Models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity {

    EditText editText_Title;
    EditText editText_Notes;
    ImageView imageView_save;
    Notes notes;
    boolean isOldNote = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);


        editText_Title = findViewById(R.id.editText_Title);
        editText_Notes = findViewById(R.id.editText_Notes);
        imageView_save = findViewById(R.id.imageView_save);

        notes = new Notes();
        try {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editText_Title.setText(notes.getTitle());
             editText_Notes.setText(notes.getNotes());
             isOldNote = true;

        }catch (Exception e){
            System.out.println(e);
        }



        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editText_Title.getText().toString();
                String description = editText_Notes.getText().toString();

                if (description.isEmpty()){
                    Toast.makeText(NotesTakerActivity.this, "PLS ENTER DESCRIPTION", Toast.LENGTH_LONG).show();
                    return;
                }
                SimpleDateFormat format  = new SimpleDateFormat("h:mm a, yyyy.MM.dd");
                Date date = new Date();

                if (!isOldNote){
                    notes = new Notes();
                }

                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDate(format.format(date));

                Intent intent = new Intent();
                intent.putExtra("notes", notes);
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });
    }
}