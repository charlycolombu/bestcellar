package com.example.caveavinmmm.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "wine_table")
public class Wine implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "photo")
    private Bitmap photo;
    @ColumnInfo(name = "vignoble")
    private String vignoble;
    @ColumnInfo(name = "nom_vin")
    private String nomVin;
    @ColumnInfo(name = "note")
    private int note;
    @ColumnInfo(name = "prix")
    private int prix;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "commentaire")
    private String commentaire;

    public Wine(String vignoble, String nomVin, String type) {
        this.vignoble = vignoble;
        this.nomVin = nomVin;
        this.type = type;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getVignoble() {
        return vignoble;
    }

    public void setVignoble(String vignoble) {
        this.vignoble = vignoble;
    }

    public String getNomVin() {
        return nomVin;
    }

    public void setNomVin(String nomVin) {
        this.nomVin = nomVin;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Wine{" +
                "id=" + id +
                ", vignoble='" + vignoble + '\'' +
                ", nom vin='" + nomVin + '\'' +
                ", note='" + note + '\'' +
                ", prix='" + prix + '\'' +
                ", type='" + type + '\'' +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
