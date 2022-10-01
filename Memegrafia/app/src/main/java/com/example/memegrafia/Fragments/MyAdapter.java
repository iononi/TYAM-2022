package com.example.memegrafia.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memegrafia.Interfaces.OnMemeSelected;
import com.example.memegrafia.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<String> memes; // almacena los nombres de los memes
    private int [] drawables; // almacena los recursos (ID) de las imagenes
    private OnMemeSelected listener;

    MyAdapter(List<String> memes, int[] drawables, OnMemeSelected listener) {
        this.memes = memes;
        this.drawables = drawables;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
        View view = inflater.inflate (R.layout.list_row_item, parent, false);
        return new MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        // se asigna la información en cada fila de la lista y se establece el manejador del evento OnClickListener,
        // de modo que al seleccionarse se invoque al método planetSelected en el objeto que manejará dicho evento
        holder.setData(memes.get(position));
        holder.setImage (drawables [position]);
        holder.itemView.setOnClickListener(view -> listener.onMemeSelected(position));
    }

    @Override
    public int getItemCount() {
        return memes.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView memeName;
        private ImageView memeImage;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //memeName = itemView.findViewById (android.R.id.memeName);
            memeName = itemView.findViewById (R.id.memeName);
            memeImage = itemView.findViewById (R.id.memeImage);
        }

        void setData(String data) {
            memeName.setText (data);
        }

        void setImage (int resourceId) {
            memeImage.setImageResource (resourceId);
        }
    }
}