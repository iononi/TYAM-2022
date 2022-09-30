package com.example.memegrafia;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.memegrafia.Fragments.Details;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView (R.layout.activity_details);

        Intent intent = getIntent ();
        if (intent == null) return;

        Bundle extras = intent.getExtras ();
        if (extras == null) return;

        int position = extras.getInt ("POSITION");

        getSupportFragmentManager ()
                .beginTransaction ()
                .add (R.id.contentDetails, new Details (position))
                .setTransition (FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit ();
    }
}
