package com.example.project;

import androidx.cardview.widget.CardView;

import com.example.project.Models.Notes;

public interface NotesClickListener {

    void onClick(Notes notes);



    void onLongClick(Notes notes, CardView cardView);

}
