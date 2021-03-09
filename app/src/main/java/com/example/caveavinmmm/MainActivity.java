package com.example.caveavinmmm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caveavinmmm.data.UserDAO;
import com.example.caveavinmmm.data.UserDatabase;
import com.example.caveavinmmm.model.User;

public class MainActivity extends AppCompatActivity {
    UserDatabase dataBase;
    UserDAO db;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        SharedPreferences mPrefs = getSharedPreferences("bestcellar", 0);
        String mail = mPrefs.getString("mail", "");
        dataBase = UserDatabase.getInstance(this);
        db = dataBase.getUserDao();
        currentUser = db.findUserByMail(mail);

        if(currentUser != null) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}