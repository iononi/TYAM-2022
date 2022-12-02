package com.example.ingenio;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.ingenio.Fragments.DetallesFragment;

public class DetallesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        Intent intent = getIntent();
        if (intent == null) return;

        Bundle extras = intent.getExtras();
        if(extras == null) return;

        int position = extras.getInt("POSITION");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentDetalles, new DetallesFragment(position))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }
}
