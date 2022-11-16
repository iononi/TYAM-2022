package com.example.ingenio;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.example.ingenio.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FormViewModel  formViewModel;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        var view1 = binding.getRoot();
        setContentView(view1);

        formViewModel = new ViewModelProvider(this).get (FormViewModel.class);

        //Ando viendo si esta parte será necesaria en esta activity, pero cualquier cosa se borra
        //Es que a veces me hago bolas porque mi tio lo hace como de mil formas diferentes y no se cual es la buena
        binding.editTextTextEmailAddress.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){}

            @Override
            public  void afterTextChanged(Editable editable){
                formViewModel.email = editable.toString();
            }
        });

        binding.editTextTextPassword.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){}

            @Override
            public  void afterTextChanged(Editable editable){
                formViewModel.password = editable.toString();
            }
        });

        //intent que nos dirigira a la activity de registro
        binding.btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), RegisterActivity.class);

            startActivity(intent);
        });

        //desarrollo y entrada al método de login
        auth = FirebaseAuth.getInstance();

        EditText edtEmail = view1.findViewById(R.id.editTextTextEmailAddress);
        EditText edtPassword = view1.findViewById(R.id.editTextTextPassword);

        Button btnLogin = view1.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            login(edtEmail.getText().toString(), edtPassword.getText().toString());
        });

        // Inicio de sesion con microsoft
        binding.msLogin.setOnClickListener (v -> {
            microsoftLogin ();
        });

    }

    private void login (String email, String password){
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();
                        String name = "";

                        if(user != null){
                            name = user.getDisplayName();
                        }

                        Toast.makeText(getBaseContext(),"Usuario" + name, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getBaseContext(), PresentActivity.class);

                        startActivity(intent);
                    } else {
                        Toast.makeText(getBaseContext(),"Usuario y/o contraseña no reconocida",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void microsoftLogin () {
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");
        provider.addCustomParameter ("prompt", "consent"); // pide confirmación para iniciar sesión
        Task<AuthResult> pendingResultTask = auth.getPendingAuthResult ();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingSignIn (pendingResultTask);
        } else {
            // There's no pending result so you need to start the sign-in flow.
            startNewSignInOperation (provider);
        }
    }

    private void pendingSignIn (Task<AuthResult> pendingTask) {
        pendingTask.addOnSuccessListener (authResult -> {
            // User is signed in.
            // IdP data available in
            // authResult.getAdditionalUserInfo().getProfile().
            // The OAuth access token can also be retrieved:
            // ((OAuthCredential)authResult.getCredential()).getAccessToken().
            // The OAuth ID token can also be retrieved:
            // ((OAuthCredential)authResult.getCredential()).getIdToken().
            Toast.makeText (this, "MS login successful", Toast.LENGTH_SHORT).show ();
        })
        .addOnFailureListener (e -> {
            // Handle failure.
            Toast.makeText (this, "MS login failed", Toast.LENGTH_SHORT).show ();
        });
    }

    private void startNewSignInOperation (OAuthProvider.Builder provider) {
        auth.startActivityForSignInWithProvider (this, provider.build ())
                .addOnSuccessListener (authResult -> {
                    // User is signed in.
                    // IdP data available in
                    // authResult.getAdditionalUserInfo().getProfile().
                    // The OAuth access token can also be retrieved:
                    // authResult.getCredential().getAccessToken().
                    // The OAuth ID token can also be retrieved:
                    // authResult.getCredential().getIdToken().
                    Toast.makeText (this, "MS login successful", Toast.LENGTH_SHORT).show ();
                    Intent presentActivity = new Intent (this, PresentActivity.class);
                    startActivity (presentActivity);
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Handle failure.
                    Log.e ("Ingenio", e.toString ());
                    Toast.makeText (this, "MS login failed startActivity", Toast.LENGTH_SHORT).show ();
                })
                .addOnCanceledListener (() -> {
                    Toast.makeText (this, "Operation cancelled", Toast.LENGTH_SHORT).show ();
                });
    }


}
