package com.example.ingenio;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.ingenio.databinding.ActivityPerfilBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilActivity extends Activity {
    FirebaseUser currentUser;
    ActivityPerfilBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        String name, telephone, email;

        currentUser = FirebaseAuth.getInstance ().getCurrentUser (); // obtiene el usuario actual
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilBinding.inflate (getLayoutInflater ());

        var profileView = binding.getRoot ();

        // cargamos datos a la vista
        name = currentUser.getDisplayName ();
        if (name != null)
            binding.tvName.setText (name);
        else
            Toast.makeText (PerfilActivity.this, "name is null", Toast.LENGTH_SHORT).show ();

        if (currentUser.getPhotoUrl () == null) {
            Drawable profilePicture = AppCompatResources.getDrawable (getBaseContext (), R.mipmap.ic_launcher_round);
            binding.ivProfilePic.setImageDrawable (profilePicture);
        }

        telephone = currentUser.getPhoneNumber ();
        binding.tvTelephone.setText (telephone);

        email = currentUser.getEmail ();
        binding.tvAddress.setText (email);
        setContentView(profileView);
    }
}
