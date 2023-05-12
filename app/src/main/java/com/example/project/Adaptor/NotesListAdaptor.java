package com.example.project.Adaptor;

import android.content.Context;
import android.location.GnssAntennaInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.project.Models.Notes;
import com.example.project.NotesClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class NotesListAdaptor extends RecyclerView.Adapter <NotesViewHolder> {

    Context context;
    List<Notes> list;

    NotesClickListener notesClickListener;

    public NotesListAdaptor(Context context, List<Notes> list, NotesClickListener notesClickListener) {
        this.context = context;
        this.list = list;
        this.notesClickListener = notesClickListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

    holder.textView_title.setText(list.get(position).getTitle());
    holder.textView_title.setSelected(true);

    holder.TextView_notes.setText(list.get(position).getNotes());

    holder.TextView_date.setText(list.get(position).getDate());
    holder.TextView_date.setSelected(true);

    if (list.get(position).isPinned()){
        holder.imageView_pin.setImageResource(R.drawable.icon_pin);
    } else {
        holder.imageView_pin.setImageResource(0);
    }
        int colorCode = getRandomColor();
        holder.notes_conteiner.setCardBackgroundColor(holder.itemView.getResources().getColor(colorCode, null));

        holder.notes_conteiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesClickListener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_conteiner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                notesClickListener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_conteiner);
                return true;
            }
        });

    }


    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.blueLight);
        colorCode.add(R.color.blue);
        colorCode.add(R.color.teal);

        Random random = new Random();
        int randomColor = random.nextInt(colorCode.size());
        return randomColor;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}


class NotesViewHolder extends RecyclerView.ViewHolder {

    CardView notes_conteiner;
    TextView textView_title, TextView_notes, TextView_date;
    ImageView imageView_pin;




    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

        notes_conteiner = itemView.findViewById(R.id.notes_conteiner);
        textView_title = itemView.findViewById(R.id.textView_title);
        TextView_notes = itemView.findViewById(R.id.TextView_notes);
        TextView_date = itemView.findViewById(R.id.TextView_date);
        imageView_pin = itemView.findViewById(R.id.imageView_pin);


    }
}