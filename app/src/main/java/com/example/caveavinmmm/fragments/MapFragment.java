package com.example.caveavinmmm.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.caveavinmmm.R;
import com.example.caveavinmmm.adapters.WineElement;
import com.example.caveavinmmm.data.UserDatabase;
import com.example.caveavinmmm.data.WineDAO;
import com.example.caveavinmmm.model.Wine;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private MapView mapView;
    private GoogleMap mGoogleMap;
    private BitmapDescriptor wineMarkerIcon;

    WineDAO db;
    UserDatabase dataBase;
    ArrayList<WineElement> listWines = new ArrayList<WineElement>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this); //this is important

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(48.117266
                , -1.6777926
        ))); // coordonnées de Rennes
        mGoogleMap.setMinZoomPreference(5.5f); // Ordre de grandeur sur la France entière
        //wineMarkerIcon = BitmapDescriptorFactory.fromAsset(String.valueOf(R.drawable.ic_wine_marker)); TODO : convertir drawable en bitmap
       setMarkersReady();
    }

    public void setMarkersReady() {
        /*dataBase = Room.databaseBuilder(this.getContext(), UserDatabase.class, "vin-database.db")
                .allowMainThreadQueries()
                .build();

        db = dataBase.getWineDao();
        List<Wine> wine = db.getWine();
        Iterator<Wine> it = wine.iterator();
        while (it.hasNext()){
            Wine element = it.next();
            listWines.add(new WineElement(element.getNomVin(), element.getVignoble(), element.getVille()));
        }

        for (WineElement wineElement: listWines) {
            rechercheCoordonnees(wineElement.villeVin);
        }*/

        mGoogleMap.addMarker(new MarkerOptions().title("Rennes").icon(bitmapDescriptorFromVector(this.getContext(), R.drawable.ic_beer_marker))
.position(new LatLng(48.117266
                , -1.6777926
        )));// coordonnées de Rennes

        mGoogleMap.addMarker(new MarkerOptions().title("Nantes").icon(bitmapDescriptorFromVector(this.getContext(), R.drawable.ic_wine_marker))
.position(new LatLng(47.2172500
                , -1.5533600
        )));// coordonnées de Nantes
        // Utilisation de la méthode créée plus haut.
        //String adresseATransformer = "12 rue de la Convention, 75615, Paris";


    }

   /* private void rechercheCoordonnees(String ville) {
        Geocoder geocoder = new Geocoder(this.getContext(), Locale.FRANCE);
        List<Address> wines = null;
        try {
            wines = geocoder.getFromLocationName(ville, 5);
            for (Address address : wines) {
                double mlatitude = address.getLatitude();
                double mlongitude = address.getLatitude();
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(mlatitude
                        , mlongitude
                )));
            }
        } catch (IOException e) {
            Log.e("Geocoder", e.getMessage());
        }
    }*/


    @Override
    public boolean onMarkerClick(Marker marker) {

        //TODO: afficher cards
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(80, 40, vectorDrawable.getIntrinsicWidth() + 80, vectorDrawable.getIntrinsicHeight() + 40);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
