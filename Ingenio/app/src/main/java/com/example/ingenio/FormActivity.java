package com.example.ingenio;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ingenio.databinding.ActivityFormBinding;

public class FormActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    ActivityFormBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityFormBinding.inflate (getLayoutInflater ());
        var registerView = binding.getRoot ();
        setContentView(registerView);
//        SABER SI LA FOTO EN EL IMAGEVIEW DE FOTO DE PERFIL ES LA DE PERSONA
//        ImageView view = binding.ivProfilePic;
//        view.setTag (R.mipmap.profile);
//        view.setOnClickListener (v -> {
//            ImageView imageView = (ImageView) v;
//            assert (R.id.ivProfilePic == imageView.getId ());
//
//            // See here
//            Integer integer = (Integer) imageView.getTag();
//            integer = integer == null ? 0 : integer;
//
//            System.out.println("Integer: " + integer);
//            if (integer == R.mipmap.profile) {
//                System.out.println("Es la foto de persona");
//            }
//        });
//        Drawable profilePicture = binding.ivProfilePic.getDrawable ();
//        System.out.println ("Foto de perfil: " + profilePicture);
        binding.btnChangePic.setOnClickListener (view -> {
            if (checkSelfPermission (Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions (new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult (cameraIntent, CAMERA_REQUEST);
            }
        });
        //TODO: Implementar funcionalidad en el botón "Guardar"
        // El botón debe crear un perfil en Realtime Database e iniciar sesión
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
