package com.example.ingenio;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ingenio.Models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.ingenio.databinding.ActivityFormBinding;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Vector;

public class FormActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    ActivityFormBinding binding;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance (); // obtiene la referencia a la BD
        users = database.getReference ("users"); // obtiene la referencia al nodo users
        // modifica estos valores para ingresar un nuevo usuario
        //Users kevin = new Users ("Kevin", "Salgado", "sgkeving@gmail.com", 123, "13/07/2000", "2295062034");
        //saveNewUser ("kevin", kevin);
        printDatabaseChildren (users);
        binding = ActivityFormBinding.inflate (getLayoutInflater ());
        var registerView = binding.getRoot ();
        setContentView(registerView);

        // pone una etiqueda en el IV con el ID de la foto @mipmap/profile
        // servirá más adelante para saber si se cargó una foto (tomó foto con la cámara) o no
        binding.ivProfilePic.setTag (R.mipmap.profile);
        binding.btnChangePic.setOnClickListener (view1 -> {
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
        binding.btnRegister.setOnClickListener (view2 -> {
            // checar por campos obligatorios: Nombre, apellidos, correo, contraseña
            boolean hasNoName = binding.edtName.getText ().toString ().isEmpty ();
            boolean hasNoLastName = binding.edtlastName.getText ().toString ().isEmpty ();
            boolean hasNoEmail = binding.edtEmail.getText ().toString ().isEmpty ();
            boolean hasNoPassword = binding.edtPassword.getText ().toString ().isEmpty ();
            boolean promptMissingFieldsWarning = false;
            if (hasNoName) {
                binding.edtlastName.setError ("Por favor llena este campo");
                promptMissingFieldsWarning = true;
            }
            if (hasNoLastName) {
                binding.edtName.setError ("Por favor llena este campo");
                promptMissingFieldsWarning = true;
            }
            if (hasNoEmail) {
                binding.edtEmail.setError ("Por favor llena este campo");
                promptMissingFieldsWarning = true;
            }

            if (hasNoPassword) {
                binding.edtPassword.setError ("Por favor llena este campo");
                promptMissingFieldsWarning = true;
            }

            if (promptMissingFieldsWarning)
                Toast.makeText (this, "Por favor, rellena los campos marcados", Toast.LENGTH_SHORT).show ();
            else {
                // TODO: Crear perfil en FB con los datos ingresados e iniciar sesión
                if (hasDefaultProfilePicture ())
                    // no subir foto a FB
                    System.out.println("Tiene la foto de perfil predeterminada");
                else
                    // subir imagen a FB
                    System.out.println("Tiene foto de perfil");
            }
        });
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

    private boolean hasDefaultProfilePicture () {
        Integer tagCode = (Integer) binding.ivProfilePic.getTag();
        tagCode = (tagCode == null) ? 0 : tagCode;

        return tagCode == R.mipmap.profile; // si es diferente de @mipmap/profile, tiene foto
    }

    private void printDatabaseChildren (DatabaseReference reference) {
        //Vector<Users> vUsers = new Vector<>();
        reference.addListenerForSingleValueEvent (new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                // datasnapShot hace referencia a la rama users
                // el método getChildren devuelve cada subelemento user
                for (DataSnapshot item: dataSnapshot.getChildren ()) {
                    Log.d ("TAG", item.getKey() + " - " +  item.getValue ().toString());
                    // getValue devuelve referencia al objeto contenido en cada nodo
                    // y lo convierte al tipo de datos indicado en el parámetro
                    Users user = item.getValue (Users.class);
          //          vUsers.add (user);
                    Log.d ("TAG", user.getName() + " " + user.getLastName());
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {
                Log.e ("Ingenio", databaseError.getMessage ());
            }
        });
    }

    /**
     * agrega un nuevo elemento a la rama Userss
     */
    private void saveNewUser (String nodeName, Users newUser) {
        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        DatabaseReference users = database.getReference ("users");

        HashMap<String, Object> node = new HashMap<>();
        node.put (nodeName, newUser);

        users.updateChildren (node)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText (getBaseContext (), "Ndde added successfully", Toast.LENGTH_LONG).show ();
                })
                .addOnFailureListener(e -> Toast.makeText (getBaseContext (), "Ndde add failed: " + e.getMessage (), Toast.LENGTH_LONG).show ());
    }
}
