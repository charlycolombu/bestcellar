package com.example.caveavinmmm.fragments;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.caveavinmmm.LoginActivity;
import com.example.caveavinmmm.MainActivity;
import com.example.caveavinmmm.R;
import com.example.caveavinmmm.adapters.WineAdapter;
import com.example.caveavinmmm.adapters.WineElement;
import com.example.caveavinmmm.data.UserDatabase;
import com.example.caveavinmmm.data.WineDAO;
import com.example.caveavinmmm.model.Wine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccueilFragment extends Fragment {

    private Button wineButton;
    private Button beerButton;
    private ListView drinkList;

    ArrayList<WineElement> listItems = new ArrayList<WineElement>();
    WineAdapter adapter;

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
                clearList();
                addWine();
            }
        });

        beerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearList();
            }
        });

        return inflatedView;
    }

    public void addWine(){
        List<Wine> wine = db.getWine();
        Iterator<Wine> it = wine.iterator();
        while (it.hasNext()){
            Wine element = it.next();
            listItems.add(new WineElement(element.getPhoto(), element.getNomVin(), element.getVignoble()));
        }
        
        adapter = new WineAdapter(this.getContext(), R.layout.adapter_view_layout, listItems);

        drinkList.setAdapter(adapter);

        drinkList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        DetailFragment detailFragment = new DetailFragment(wine.get(position));
                        ft.replace(R.id.fragment_container, detailFragment);
                        ft.commit();
                    }
                }
        );
    }

    public void clearList(){
        listItems.clear();

        adapter = new WineAdapter(this.getContext(), R.layout.adapter_view_layout, listItems);

        drinkList.setAdapter(adapter);
    }
}
