package com.example.ingenio;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class PerfilActivity extends Activity {
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1;
    FirebaseUser currentUser;
    ActivityPerfilBinding binding;
    Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        String email;
        FirebaseStorage storage = FirebaseStorage.getInstance();
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
                if(String.valueOf(dataSnapshot.child("name").getValue()) != "null"){
                    name.setText(String.valueOf(dataSnapshot.child("name").getValue()));
                }
                if(String.valueOf(dataSnapshot.child("lastName").getValue()) != "null"){
                    lastName.setText(String.valueOf(dataSnapshot.child("lastName").getValue()));
                }
                if(String.valueOf(dataSnapshot.child("birthday").getValue()) != "null"){
                    birthday.setText(String.valueOf(dataSnapshot.child("birthday").getValue()));
                }
                if(String.valueOf(dataSnapshot.child("phoneNumber").getValue()) != "null"){
                    phoneNumber.setText(String.valueOf(dataSnapshot.child("phoneNumber").getValue()));
                }
                String imageURL = String.valueOf(dataSnapshot.child("profilePictureUrl").getValue());
                //ahora vamos a cargar la imagen desde FBStorage

                if (photo != null && imageURL != "null") {
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

        // cargamos datos a la vista en caso de que hagamos inicio de sesion con microsoft o google
        // ya que si iniciamos sesion de manera local, currentUser no tiene la propiedad de name
        String nombre = currentUser.getDisplayName ();
        if (nombre != null)
            binding.tvName.setText (nombre);

        if (binding.ivProfilePic == null) {
            //Drawable profilePicture = AppCompatResources.getDrawable (getBaseContext (), R.mipmap.ic_launcher_round);
            //binding.ivProfilePic.setImageDrawable (profilePicture);
            binding.ivProfilePic.setTag (R.mipmap.profile);
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

        //ponermos lo necesario para tomarse la foto
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

        //btn de actualizar
        binding.btnUpdateProfile.setOnClickListener(view2 ->{
            String name1 = binding.tvName.getText().toString();
            String lastName1 = binding.tvlastName.getText().toString();
            //String email1 = binding.tvEmail.getText().toString();
            String birthday1 = binding.tvBirthday.getText().toString();
            String phoneNumber1 = binding.tvTelephone.getText().toString();


            if(!hasDefaultProfilePicture()){
                ImageView iv = binding.ivProfilePic;
                StorageReference imagesFolder = storage.getReference ("/UsersFotos");
                StorageReference  image = imagesFolder.child (email);

                ByteArrayOutputStream bos = new ByteArrayOutputStream ();
                Bitmap bitmap = getBitmapFromDrawable (iv.getDrawable ());
                bitmap.compress (Bitmap.CompressFormat.PNG, 100, bos);
                byte [] buffer = bos.toByteArray ();

                image.putBytes (buffer)
                        .addOnFailureListener (e -> {
                            Toast.makeText (getBaseContext (), "Error uploading file: " + e.getMessage (), Toast.LENGTH_LONG).show ();
                            Log.e ("INGENIO", "Error uploading file: " + e.getMessage ());
                        })
                        .addOnCompleteListener (task -> {
                            if (task.isComplete ()) {
                                Task<Uri> getUriTask = image.getDownloadUrl ();

                                getUriTask.addOnCompleteListener (t -> {
                                    uri = t.getResult ();
                                    if (uri == null) return;

                                    //guardo los datos en el objeto y de esa manera los mando a guardar en realtime
                                    Users user = new Users (birthday1, email, lastName1, name1, phoneNumber1, uri.toString());
                                    saveNewUser(uid,user);
                                    //registerUser(email,contrasena, user);

                                    //Toast.makeText (getBaseContext (), "Download URL " + uri.toString (), Toast.LENGTH_LONG).show ();
                                    Log.i ("INGENIO", "Download URL " + uri.toString ());
                                });
                            }
                        });
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

    private Bitmap getBitmapFromDrawable (Drawable drble) {
        // debido a la forma que el sistema dibuja una imagen en un el sistema gráfico
        // es necearios realzar comprobaciones para saber del tipo de objeto Drawable
        // con que se está trabajando.
        //
        // si el objeto recibido es del tipo BitmapDrawable no se requieren más conversiones
        if (drble instanceof BitmapDrawable) {
            return  ((BitmapDrawable) drble).getBitmap ();
        }

        // en caso contrario, se crea un nuevo objeto Bitmap a partir del contenido
        // del objeto Drawable
        Bitmap bitmap = Bitmap.createBitmap (drble.getIntrinsicWidth (), drble.getIntrinsicHeight (), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drble.setBounds (0, 0, canvas.getWidth (), canvas.getHeight ());
        drble.draw (canvas);

        return bitmap;
    }

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
