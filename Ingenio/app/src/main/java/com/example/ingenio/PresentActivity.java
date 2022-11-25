package com.example.ingenio;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class PresentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ingenio");
        setActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.mnuModUser){
            Intent intent = new Intent(getBaseContext(), FormActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.mnuVerUser){
            Intent intent = new Intent(getBaseContext(), PerfilActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
