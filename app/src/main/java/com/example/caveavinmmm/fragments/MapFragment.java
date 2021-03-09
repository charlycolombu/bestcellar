package com.example.caveavinmmm.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.caveavinmmm.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private MapView mapView;
    private GoogleMap mGoogleMap;
    private BitmapDescriptor wineMarkerIcon;

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
        mGoogleMap.addMarker(new MarkerOptions().icon(wineMarkerIcon).position(new LatLng(48.117266
                , -1.6777926
        )));// coordonnées de Rennes
        /*mGoogleMap.addMarker(new MarkerOptions().icon(wineMarkerIcon).position(new LatLng(48.117266
                , -1.6777926
        )));// TODO: icon marker custom*/
    }

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

}
