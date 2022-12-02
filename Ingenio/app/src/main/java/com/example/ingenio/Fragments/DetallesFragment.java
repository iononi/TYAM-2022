package com.example.ingenio.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.ingenio.R;
import com.squareup.picasso.Picasso;

public class DetallesFragment extends Fragment {
    private int position;

    public DetallesFragment() { super(); }

    public DetallesFragment(int position){
        super();
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalles,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentActivity activity = getActivity();
        if (activity == null) return;

        String [] TituloCarrera = getResources().getStringArray(R.array.carreras);
        String [] Imagen = getResources().getStringArray(R.array.URL_Imagenes);
        String [] CarreraDetalle = getResources().getStringArray(R.array.detalles);

        TextView tvTitle = activity.findViewById(R.id.tvTitle);
        if (tvTitle != null) tvTitle.setText(TituloCarrera [position]);

        ImageView tvImage = activity.findViewById(R.id.image1);
        if (tvImage != null) {
            Picasso.get()
                    .load(Imagen[position])
                    .resize(200,200)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(tvImage);
        }

        TextView tvDetalle = activity.findViewById(R.id.tvDetalle);
        if (tvDetalle != null) tvDetalle.setText(CarreraDetalle [position]);

    }
}
