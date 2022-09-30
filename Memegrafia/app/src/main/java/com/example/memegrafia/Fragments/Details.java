package com.example.memegrafia.Fragments;

import android.graphics.drawable.Drawable;
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

import com.example.memegrafia.R;
import com.example.memegrafia.databinding.FragmentDetailsBinding;

public class Details extends Fragment {

    // Almacena el valor del elemento seleccionado de la lista, como el index de un array.
    // Es decir para una lista de 10 elementos, el valor de position del elemento 1 será 0, el del
    // elemento 2 será 1 y así sucesivamente. Con él luego se accederá a la información del string
    // array pasando como index este valor, para el cual corresponde un valor en los string array
    // de imagenes (@string/images) y name (@string/names)
    private int position;

    public Details(int position) {
        this.position = position;
    }

    public Details() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentDetailsBinding detailsBinding = FragmentDetailsBinding.inflate ( getLayoutInflater() );

        FragmentActivity activity = getActivity ();
        if (activity == null)
            return;

        String [] imagesArray = getResources ().getStringArray (R.array.images);
        String [] namesArray = getResources ().getStringArray (R.array.names);
        String [] infoArray = getResources().getStringArray (R.array.info);

        TextView memeTitle = activity.findViewById (R.id.memeTitle);
        if (memeTitle != null)
            memeTitle.setText (namesArray [position]);

        ImageView memeImage = activity.findViewById (R.id.memeImage);
        if (memeImage != null) {
            Integer drawableId = (Integer) memeImage.getTag ();
            drawableId = (drawableId == null) ? 0 : drawableId;
            switch (drawableId) {
                case R.drawable.cereal_guy:
                    memeImage.setImageResource (R.drawable.cereal_guy);
                    break;
                case R.drawable.f_yeah:
                    memeImage.setImageResource (R.drawable.f_yeah);
                    break;
            }
        }

        for (String image: imagesArray) {
            System.out.println(image);
        }

        TextView memeDescription = activity.findViewById (R.id.memeDescription);
        if (memeDescription != null)
            memeDescription.setText (infoArray [position]);
    }
}
