package com.example.caveavinmmm.adapters;

import android.graphics.Bitmap;

public class WineElement {
    public Bitmap photoVin;
    public String nomVin;
    public String vignoble;
    public String villeVin;

    public WineElement(Bitmap photoVin, String name, String vignoble) {
        this.photoVin = photoVin;
        this.nomVin = name;
        this.vignoble = vignoble;
        //this.villeVin = villeVin;
    }
}
