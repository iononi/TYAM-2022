package com.example.ingenio;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;

import com.example.ingenio.Models.Users;
import com.example.ingenio.databinding.ActivityPerfilBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PerfilActivity extends Activity {
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1;
    FirebaseUser currentUser;
    ActivityPerfilBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        String email;

        currentUser = FirebaseAuth.getInstance ().getCurrentUser (); // obtiene el usuario actual
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilBinding.inflate (getLayoutInflater ());

        var profileView = binding.getRoot ();


        TextView name = binding.tvName;
        TextView lastName = binding.tvlastName;
        TextView birthday = binding.tvBirthday;
        TextView phoneNumber = binding.tvTelephone;
        ImageView photo = binding.ivProfilePic;


        //consulta de realtime
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        String uid = currentUser.getUid();
        //DatabaseReference ref = reference.child(currentUser.getUid());
        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(String.valueOf(dataSnapshot.child("name").getValue()));
                lastName.setText(String.valueOf(dataSnapshot.child("lastName").getValue()));
                birthday.setText(String.valueOf(dataSnapshot.child("birthday").getValue()));
                phoneNumber.setText(String.valueOf(dataSnapshot.child("phoneNumber").getValue()));
                String imageURL = String.valueOf(dataSnapshot.child("profilePictureUrl").getValue());
                //ahora vamos a cargar la imagen desde FBStorage

                if (photo != null) {
                    Picasso.get()
                            .load(imageURL)
                            .resize(200,200)
                            .centerCrop()
                            .placeholder(R.drawable.placeholder)
                            .into(photo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // cargamos datos a la vista
        /*name = currentUser.getDisplayName ();
        if (name != null)
            binding.tvName.setText (name);
        else
            Toast.makeText (PerfilActivity.this, "name is null", Toast.LENGTH_SHORT).show ();
        */
        if (binding.ivProfilePic == null) {
            Drawable profilePicture = AppCompatResources.getDrawable (getBaseContext (), R.mipmap.ic_launcher_round);
            binding.ivProfilePic.setImageDrawable (profilePicture);
        }

        //telephone = currentUser.getPhoneNumber ();
        //binding.tvTelephone.setText (telephone);

        email = currentUser.getEmail ();
        binding.tvEmail.setText (email);

        /*binding.btnChangePic.setOnClickListener (view -> {
            if (checkSelfPermission (Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions (new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                // como ya se tomó la foto, se pone a 0 la etiqueta del IV, 0 != @mipmap/profile
                // entonces en la llamada al método hasDefaultProfilePicture() obtendremos tagCode=0
                binding.ivProfilePic.setTag (0);
                startActivityForResult (cameraIntent, CAMERA_REQUEST);
            }
        });*/
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
