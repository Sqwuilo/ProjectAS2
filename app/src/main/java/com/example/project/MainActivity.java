package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.project.Adaptor.NotesListAdaptor;
import com.example.project.DataBase.RoomDB;
import com.example.project.Models.Notes;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    SearchView searchView_home;
    RecyclerView recyclerView;
    Button Button;
    NotesListAdaptor notesListAdaptor;
    RoomDB dataBase;
    List<Notes> notes = new ArrayList<>();
    Notes selectedNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        Button = findViewById(R.id.button_id);
        searchView_home = findViewById(R.id.searchView_home);
        dataBase = RoomDB.getInstance(this);
        notes = dataBase.mainDAO().getAll();

        updateRecycler(notes);

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter (newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<Notes> filteredList = new ArrayList<>();
        for (Notes singleNote: notes) {
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase()) ||
                    singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(singleNote);
            }
        }
        notesListAdaptor.filterList(filteredList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101){
            if (resultCode == Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("notes");
                dataBase.mainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(dataBase.mainDAO().getAll());
                notesListAdaptor.notifyDataSetChanged();
            }
        }
        if (requestCode == 102){
            if (resultCode == Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("notes");
                dataBase.mainDAO().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());
                notes.clear();
                notes.addAll(dataBase.mainDAO().getAll());
                notesListAdaptor.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdaptor = new NotesListAdaptor(MainActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(notesListAdaptor);
    }
    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
            intent.putExtra("old_note", notes);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {

            selectedNote = new Notes();
            selectedNote = notes;
            showPopUp (cardView);

        }
    };

    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pin:
                if (selectedNote.isPinned()){
                    dataBase.mainDAO().pin(selectedNote.getID(), false);
                    Toast.makeText(MainActivity.this, "unpinned", Toast.LENGTH_SHORT).show();
                }else {
                    dataBase.mainDAO().pin(selectedNote.getID(), true);
                    Toast.makeText(MainActivity.this, "pinned", Toast.LENGTH_SHORT).show();
                }
                notes.clear();
                notes.addAll(dataBase.mainDAO().getAll());
                notesListAdaptor.notifyDataSetChanged();
                return true;

            case R.id.delete:
                dataBase.mainDAO().delete(selectedNote);
                notes.remove(selectedNote);
                notesListAdaptor.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "deleted", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return false;
        }
    }
}