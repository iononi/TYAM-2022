package com.example.ingenio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.ingenio.Fragments.DetallesFragment;
import com.example.ingenio.Fragments.FragmentListado;
import com.example.ingenio.Models.Users;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PresentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView tvNameBar, tvEmailBar;
    FirebaseUser currentUser;
    FirebaseAuth auth;
    String signInProvider;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present);
        auth = FirebaseAuth.getInstance ();
        currentUser = auth.getCurrentUser (); // referencia al usuario actual
        //Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        View headerLayout = navigationView.getHeaderView (0);
        tvNameBar = headerLayout.findViewById (R.id.tvNameNavBar); // TV del header
        tvEmailBar = headerLayout.findViewById (R.id.tvEmailNavBar); // TV del header

        if (currentUser != null) {
            signInProvider = currentUser.getIdToken (false).getResult ().getSignInProvider ();

            assert signInProvider != null;
            if(!signInProvider.equals("google.com") && !signInProvider.equals("microsoft.com") && !signInProvider.equals("anonymous")){
                //FirebaseDatabase basedatos;
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
                Users users = new Users();
                String uid = currentUser.getUid();
                //DatabaseReference ref = reference.child(currentUser.getUid());
                reference.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        /*for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Log.d ("TAG", snapshot.getKey() + " - " +  snapshot.getValue ().toString());
                            Users users = snapshot.getValue(Users.class);
                        }*/


                        //users.setName(String.valueOf(dataSnapshot.child("nombre").getValue()));
                        tvNameBar.setText(String.valueOf(dataSnapshot.child("name").getValue()));
                        tvEmailBar.setText(currentUser.getEmail());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else if (!signInProvider.equals ("anonymous")) {
                tvNameBar.setText (currentUser. getDisplayName ());
                tvEmailBar.setText (currentUser.getEmail ());
            }
        }




        //Tool bar
        setSupportActionBar(toolbar);

        //Navigation Drawer Menu

        //Hide or show items (opcional, dependiendo las necesidades)

        /*Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem()*/

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        //desarrollo del recycler view
        FragmentListado f = new FragmentListado();
        f.setOnDegreeSelectedListener(position -> {
            FrameLayout layout = findViewById(R.id.contentDetalles);

            if (layout != null){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentDetalles, new DetallesFragment(position))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                Intent intent = new Intent(this,DetallesActivity.class);
                intent.putExtra("POSITION", position);
                startActivity(intent);
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container,f)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();


        //hacer la consulta de la base de datos para recuperar datos


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;

            case R.id.nav_login:
                toLogin ();
                break;

            case R.id.nav_profile:
                if (signInProvider.equals ("anonymous")){
                    Toast.makeText (PresentActivity.this, "No accesible para anonimos", Toast.LENGTH_SHORT).show ();
                    break;
                } else {
                    Intent intent1 = new Intent(PresentActivity.this, PerfilActivity.class);
                    startActivity(intent1);
                    break;
                }

            case R.id.nav_logout:
                auth.signOut ();
                Toast.makeText (PresentActivity.this, "Sesión finalizada", Toast.LENGTH_SHORT).show ();
                toLogin ();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void toLogin () {
        Intent mainActivity = new Intent(PresentActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish ();
    }

    private void getData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            String name = ds.child("name").getValue(String.class);
            Toast.makeText(getBaseContext(),"Nombre de usuario= "+name,Toast.LENGTH_LONG).show();
        }
    }


}
