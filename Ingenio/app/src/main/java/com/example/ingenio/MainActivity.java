package com.example.ingenio;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.example.ingenio.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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


}
