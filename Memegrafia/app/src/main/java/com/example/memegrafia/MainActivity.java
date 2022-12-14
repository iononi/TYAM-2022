package com.example.memegrafia;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.memegrafia.databinding.ActivityMainBinding;

import com.example.memegrafia.Fragments.Details;
import com.example.memegrafia.Fragments.MemeList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        var view = binding.getRoot();
        setContentView(view); // carga la vista principal

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar () != null) {
            getSupportActionBar ().setTitle ("Memegrafia"); // coloca el titulo en la ActionBar
        }

        // esto se ejecuta cuando se selecciona un meme
        MemeList memeList = new MemeList();
        memeList.setMemeSelectedListener(position -> {
            FrameLayout layout = findViewById (R.id.contentDetails);
//            System.out.println(layout);

            if (layout != null) {
                getSupportFragmentManager ()
                        .beginTransaction ()
                        .replace (R.id.contentDetails, new Details (position))
                        .setTransition (FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit ();
            } else {
                Intent intent = new Intent (this, DetailsActivity.class);
                intent.putExtra ("POSITION", position);
                startActivity (intent);
            }
        });

        getSupportFragmentManager ()
                .beginTransaction ()
                .add (R.id.myContainer, memeList)
                .setTransition (FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit ();
    }
}
