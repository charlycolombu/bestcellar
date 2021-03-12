package com.example.caveavinmmm.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.caveavinmmm.R;
import com.example.caveavinmmm.data.UserDatabase;
import com.example.caveavinmmm.data.WineDAO;
import com.example.caveavinmmm.model.Wine;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailFragment extends Fragment {

    View inflatedView = null;

    private TextView nomVin;
    private TextView nomVignoble;
    private TextView note;
    private EditText commentaire;

    CircleImageView _imageVin;
    TextView _nomVin;
    TextView _vignoble;

    private Wine wine;

    WineDAO db;
    UserDatabase dataBase;

    public DetailFragment(Wine wine) {
        this.wine = wine;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
/*

        this.inflatedView = inflater.inflate(R.layout.fragment_detail, container, false);

        dataBase = UserDatabase.getInstance(this.getContext());
        db = dataBase.getWineDao();

        //TODO faire passer l'id depuis le précédent fragment
        wine = db.findWineById(1);

        Bitmap photoVin = wine.getPhoto();
        String nomVin = wine.getNomVin().toString().trim();
        String nomVignoble = wine.getVignoble().toString().trim();

        nomVin.setText(wine.getNomVin().toString().trim());
        nomVignoble.setText(wine.getVignoble().toString().trim());
        note.setText(0);
        commentaire.setText(wine.getCommentaire().toString().trim());

        return inflatedView;*/
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        _nomVin = (TextView) view.findViewById(R.id.detail_nomvin);
        _vignoble = (TextView) view.findViewById(R.id.detail_vignbole);
        _imageVin = (CircleImageView) view.findViewById(R.id.detail_photo);

        dataBase = UserDatabase.getInstance(this.getContext());
        db = dataBase.getWineDao();

        _nomVin.setText(wine.getNomVin());
        _vignoble.setText(wine.getVignoble());
        _imageVin.setImageBitmap(wine.getPhoto());
    }
}
