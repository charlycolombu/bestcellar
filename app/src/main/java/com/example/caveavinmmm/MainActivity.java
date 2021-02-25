package com.example.caveavinmmm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        SharedPreferences mPrefs = getSharedPreferences("bestcellar", 0);
        String mail = mPrefs.getString("mail", "");
        if(mail != "") {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}