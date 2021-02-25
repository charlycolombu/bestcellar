package com.example.caveavinmmm;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.caveavinmmm.fragments.AccueilFragment;
import com.example.caveavinmmm.fragments.MapFragment;
import com.example.caveavinmmm.fragments.ProfileFragment;
import com.example.caveavinmmm.fragments.RechercheFragment;
import com.example.caveavinmmm.fragments.WishlistFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pub.devrel.easypermissions.EasyPermissions;

public class MenuActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_accueil:
                        selectedFragment = new AccueilFragment();
                        break;
                    case R.id.nav_recherche:
                        selectedFragment = new RechercheFragment();
                        break;
                    case R.id.nav_map:
                        selectedFragment = new MapFragment();
                        break;
                    case R.id.nav_wishlist:
                        selectedFragment = new WishlistFragment();
                        break;
                    case R.id.nav_profil:
                        selectedFragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AccueilFragment()).commit();
        }

        FloatingActionButton fab = findViewById(R.id.btn_photo);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, "Bouton photo appuyé", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MenuActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CODE);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });
    }
}
