package com.example.caveavinmmm.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.caveavinmmm.LoginActivity;
import com.example.caveavinmmm.MenuActivity;
import com.example.caveavinmmm.R;
import com.example.caveavinmmm.data.UserDAO;
import com.example.caveavinmmm.data.UserDatabase;
import com.example.caveavinmmm.model.User;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    TextView _name;
    Button _avisButton;
    Button _produitsButton;
    Button _disconnectButton;
    CircleImageView _profileImage;

    private Uri imageUri;
    private String myUri = "";

    UserDAO db;
    UserDatabase dataBase;
    User currentUser;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _name = (TextView) view.findViewById(R.id.name);
        _disconnectButton = (Button) view.findViewById(R.id.btn_deconnect);
        _profileImage = (CircleImageView) view.findViewById(R.id.btn_photo);

        SharedPreferences mPrefs = this.getActivity().getSharedPreferences("bestcellar", 0);
        String mail = mPrefs.getString("mail", "");

        dataBase = Room.databaseBuilder(this.getActivity(), UserDatabase.class, "vin-database.db")
                .allowMainThreadQueries()
                .build();

        db = dataBase.getUserDao();
        currentUser = db.findUserByMail(mail);

        _name.setText(currentUser.getUserName());

        _disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrefs.edit().clear().commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
 }
