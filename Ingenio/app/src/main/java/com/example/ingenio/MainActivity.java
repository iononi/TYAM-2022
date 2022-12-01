package com.example.ingenio;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.example.ingenio.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FormViewModel  formViewModel;
    private FirebaseAuth auth;
    public static final String GOOGLE_ID_CLIENT_TOKEN = Secretos.GOOGLE_ID_CLIENT_TOKEN;
    private GoogleSignInClient gmsClient;
    public static final int GOOGLE_SIGNIN_REQUEST_CODE = 1001;

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
            Intent intent = new Intent(getBaseContext(), FormActivity.class);

            startActivity(intent);
        });

        //desarrollo y entrada al método de login
        auth = FirebaseAuth.getInstance();

        EditText edtEmail = view1.findViewById(R.id.editTextTextEmailAddress);
        EditText edtPassword = view1.findViewById(R.id.editTextTextPassword);

        Button btnLogin = view1.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            // Añade validacion de campos. Si presiona iniciar sesion con los campos vacios, truena
            if (edtEmail.getText ().toString ().isEmpty () || edtPassword.getText ().toString ().isEmpty ())
                Toast.makeText (this, "Asegúrese de llenar los campos", Toast.LENGTH_SHORT).show ();
            else
                login(edtEmail.getText().toString(), edtPassword.getText().toString());
        });

        // Inicio de sesion con microsoft
        binding.msLogin.setOnClickListener (v -> {
            microsoftLogin ();
        });

        //desarrollo del login con Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(GOOGLE_ID_CLIENT_TOKEN)
                .requestEmail()
                .build();

        gmsClient = GoogleSignIn.getClient(getBaseContext(), gso);

        SignInButton sign_in_button_google = view1.findViewById(R.id.sign_in_button_google);
        sign_in_button_google.setOnClickListener(v ->{
            showGoogleSignInView();
        });


        //desarrollo del login de forma anónima
        Button btnAno = view1.findViewById(R.id.btnAnonimo);
        btnAno.setOnClickListener(v -> {
            logAnonimo();
        });
    }

    private void logAnonimo () {
        auth = FirebaseAuth.getInstance();
        auth.signInAnonymously ()
                //.addOnSuccessListener(authResult -> Log.i ("TYAM", authResult.toString ()))
                .addOnSuccessListener(task -> {
                    Toast.makeText(getBaseContext(),"Inicio de sesion anonima exitoso",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), PresentActivity.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> Log.e ("TYAM", e.getMessage ()));
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

    private void showGoogleSignInView(){
        auth = FirebaseAuth.getInstance();
        Intent intent = gmsClient.getSignInIntent();
        //myActivityResultLauncher.launch (intent);
        startActivityForResult(intent, GOOGLE_SIGNIN_REQUEST_CODE);

    }

    ActivityResultLauncher<Intent> myActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == GOOGLE_SIGNIN_REQUEST_CODE){
                    if(result.getData() == null) return;
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());

                    GoogleSignInAccount account = task.getResult();
                    if (account != null) firebaseAuthWithGoogleServices(account);
                }
            }
    );

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(data == null) return;

        if(requestCode == GOOGLE_SIGNIN_REQUEST_CODE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account != null) firebaseAuthWithGoogleServices(account);
            }
            catch (ApiException ex){
                Log.w("TYAM","Google siging failure " + ex.getMessage(),ex);
            }
        }
    }

    private void firebaseAuthWithGoogleServices(@NonNull GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getBaseContext(),"Google SigIn Succesful!",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getBaseContext(), PresentActivity.class);

                        startActivity(intent);
                    } else {
                        Toast.makeText(getBaseContext(), "SignIn with Google services failded with exception "+
                                (task.getException() != null ? task.getException().getMessage() : "None"), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
