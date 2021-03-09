package com.example.caveavinmmm.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.caveavinmmm.R;
import com.example.caveavinmmm.data.UserDatabase;
import com.example.caveavinmmm.data.WineDAO;
import com.example.caveavinmmm.model.Wine;

import java.util.Iterator;
import java.util.List;

public class AccueilFragment extends Fragment {

    private Button wineButton;
    private Button beerButton;
    private ListView drinkList;

    View inflatedView = null;

    WineDAO db;
    UserDatabase dataBase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflatedView = inflater.inflate(R.layout.fragment_accueil, container, false);
        wineButton = (Button) inflatedView.findViewById(R.id.wine_button);
        beerButton = (Button) inflatedView.findViewById(R.id.beer_button);
        drinkList = (ListView) inflatedView.findViewById(R.id.drink_list);

        dataBase = Room.databaseBuilder(this.getContext(), UserDatabase.class, "vin-database.db")
                .allowMainThreadQueries()
                .build();

        db = dataBase.getWineDao();

        wineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Wine> wine = db.getWine();
                Iterator<Wine> it = wine.iterator();
                while (it.hasNext()){
                    Toast.makeText(getActivity(), it.next().getNomVin(), Toast.LENGTH_LONG).show();
                }
            }
        });

        beerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "text", Toast.LENGTH_LONG).show();
            }
        });

        return inflatedView;
    }

}
