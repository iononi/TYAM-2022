package com.example.memegrafia.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memegrafia.R;

import java.util.Arrays;
import java.util.List;

import com.example.memegrafia.Interfaces.OnMemeSelected;

public class MemeList extends Fragment {
    private OnMemeSelected listener;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // infla el diseño del recycler view
        return inflater.inflate (R.layout.fragment_list, container, false);
    }

    public void setMemeSelectedListener(OnMemeSelected listener) {
        this.listener = listener;
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated (savedInstanceState);

        // el método getActivity regresa una referencia a la actividad que hospeda al fragmento
        FragmentActivity activity = getActivity ();
        if (activity == null) return;

        String [] namesArray = activity.getResources ().getStringArray (R.array.names);
        int [] drawables = {R.drawable.cereal_guy, R.drawable.f_yeah, R.drawable.forever_alone,
            R.drawable.freddie_mercury, R.drawable.lol_guy, R.drawable.neil_degrasse_tyson,
            R.drawable.oh_crap, R.drawable.okay, R.drawable.rage_guy, R.drawable.troll_face,
            R.drawable.y_u_no_guy, R.drawable.yao_ming};
        List<String> memes = Arrays.asList (namesArray);

        // referencia al recycler view
        RecyclerView recyclerView = activity.findViewById (R.id.myList);
        if (recyclerView ==  null) return; // no existe recycler view

        recyclerView.setLayoutManager (new GridLayoutManager(activity, 2));

        // se crea un objeto MyAdapter al que se pasan los parámetros necesarios
        // context: la actividad que hospeda al fragmento
        // List<Memes>: la lista con la información al mostrar
        // OnMemeSelected: el objeto listener mediante el cuál se invocará al manejador del evento memeSelected para los elementos de la lista
        recyclerView.setAdapter (new MyAdapter (memes, drawables, this.listener));
    }
}
