package com.example.caveavinmmm.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.caveavinmmm.R;
import com.example.caveavinmmm.data.UserDatabase;
import com.example.caveavinmmm.data.WineDAO;
import com.example.caveavinmmm.model.Wine;

public class DetailFragment extends Fragment {

    View inflatedView = null;

    private TextView nomVin;
    private TextView nomVignoble;
    private TextView note;
    private EditText commentaire;

    private Wine wine;

    WineDAO db;
    UserDatabase dataBase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.fragment_detail, container, false);

        db = dataBase.getWineDao();

        //TODO faire passer l'id depuis le précédent fragment
        wine = db.findWineById(1);

        nomVin.setText(wine.getNomVin());
        nomVignoble.setText(wine.getVignoble());
        note.setText(wine.getNote());
        commentaire.setText(wine.getCommentaire());

        return inflatedView;
    }
}
