package com.example.caveavinmmm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.caveavinmmm.data.UserDAO;
import com.example.caveavinmmm.data.UserDatabase;
import com.example.caveavinmmm.model.User;

public class SignupActivity extends AppCompatActivity {

    EditText _nameText;
    EditText _emailText;
    EditText _passwordText;
    EditText _reEnterPasswordText;
    Button _signupButton;
    TextView _loginLink;

    private UserDAO userDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        _nameText = (EditText) findViewById(R.id.input_name);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _reEnterPasswordText = (EditText) findViewById(R.id.input_reEnter_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);

        SpannableString ss = new SpannableString(_loginLink.getText());

        ForegroundColorSpan fcsBlack = new ForegroundColorSpan(Color.BLACK);
        ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.parseColor("#806540"));

        ss.setSpan(fcsBlack, 0, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcsRed, 23, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        _loginLink.setText(ss);

        userDao = UserDatabase.getInstance(this).getUserDao();

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = _nameText.getText().toString().trim();
                String email = _emailText.getText().toString().trim();
                String password = _passwordText.getText().toString().trim();
                String passwordConf = _reEnterPasswordText.getText().toString().trim();

                if (password.equals(passwordConf)) {
                    User user = new User(userName,password,email);
                    userDao.insert(user);
                    Intent moveToLogin = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(moveToLogin);
                } else {
                    Toast.makeText(SignupActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
