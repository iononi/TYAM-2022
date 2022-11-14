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


import com.example.ingenio.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    FormViewModel formViewModel;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        var view2 = binding.getRoot();
        setContentView(view2);

        formViewModel = new ViewModelProvider(this).get (FormViewModel.class);

        //Ando viendo si esta parte será necesaria en esta activity, pero cualquier cosa se borra
        //Es que a veces me hago bolas porque mi tio lo hace como de mil formas diferentes y no se cual es la buena
        binding.editEmail.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){}

            @Override
            public  void afterTextChanged(Editable editable){
                formViewModel.email = editable.toString();
            }
        });

        binding.editContra.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){}

            @Override
            public  void afterTextChanged(Editable editable){
                formViewModel.password = editable.toString();
            }
        });

        //Desarrollar el intent de iniciar sesion de manera local con firebase

        auth = FirebaseAuth.getInstance();

        EditText edtEmail = view2.findViewById(R.id.editEmail);
        EditText edtPassword = view2.findViewById(R.id.editContra);

        Button btnRegister = view2.findViewById(R.id.btnRegistrarse);
        btnRegister.setOnClickListener(v -> {
            registerUser(edtEmail.getText().toString(),edtPassword.getText().toString());
        });



    }

    private void registerUser (String email, String password) {
        auth.createUserWithEmailAndPassword (email, password)
                .addOnCompleteListener (task -> {
                    if (task.isSuccessful ()) {
                        Toast.makeText (getBaseContext(), "Register completed!", Toast.LENGTH_LONG).show ();
                    } else {
                        if (task.getException () != null) {
                            Log.e("ingenio", task.getException().getMessage());
                        }

                        Toast.makeText (getBaseContext(), "Register failed!", Toast.LENGTH_LONG).show ();
                    }
                });
    }
}
