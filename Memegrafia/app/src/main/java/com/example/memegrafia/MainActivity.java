package com.example.memegrafia;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;



import com.example.memegrafia.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        var view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar () != null) {
            getSupportActionBar ().setTitle ("Memegrafia");
        }
        FragmentListado f = new  FragmentListado ();

        getSupportFragmentManager ()
                .beginTransaction ()
                .add (R.id.myContainer, f)
                .setTransition (FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit ();

    }
}
