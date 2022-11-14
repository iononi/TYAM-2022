package com.example.ingenio;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.example.ingenio.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FormViewModel  formViewModel;

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

        //Desarrollar el intent de iniciar sesion de manera local con firebase


        binding.btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), RegisterActivity.class);

            startActivity(intent);
        });

    }
}
