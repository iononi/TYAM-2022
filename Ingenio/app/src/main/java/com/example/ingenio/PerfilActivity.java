package com.example.ingenio;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;

import com.example.ingenio.databinding.ActivityPerfilBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilActivity extends Activity {
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1;
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
        binding.tvEmail.setText (email);
        binding.ivProfilePic.setImageDrawable (ResourcesCompat.getDrawable(getResources (), R.mipmap.profile, null));
        binding.btnChangePic.setOnClickListener (view -> {
            if (checkSelfPermission (Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions (new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                // como ya se tomó la foto, se pone a 0 la etiqueta del IV, 0 != @mipmap/profile
                // entonces en la llamada al método hasDefaultProfilePicture() obtendremos tagCode=0
                binding.ivProfilePic.setTag (0);
                startActivityForResult (cameraIntent, CAMERA_REQUEST);
            }
        });
        setContentView(profileView);
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText (this, "camera permission granted", Toast.LENGTH_LONG).show ();
                Intent cameraIntent = new Intent (android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult (cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText (this, "camera permission denied", Toast.LENGTH_LONG).show ();
            }
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras ().get ("data");
            binding.ivProfilePic.setImageBitmap (photo);
        }
    }
}
