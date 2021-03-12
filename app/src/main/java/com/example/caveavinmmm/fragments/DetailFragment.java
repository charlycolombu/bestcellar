package com.example.caveavinmmm.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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
    RatingBar _note;
    EditText _comment;
    Button _btnComment;

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
        _note = (RatingBar) view.findViewById(R.id.detail_note);
        _comment = (EditText) view.findViewById(R.id.detail_comment);
        _btnComment = (Button) view.findViewById(R.id.btn_comment);

        dataBase = UserDatabase.getInstance(this.getContext());
        db = dataBase.getWineDao();

        _nomVin.setText(wine.getNomVin());
        _vignoble.setText(wine.getVignoble());
        _imageVin.setImageBitmap(wine.getPhoto());
        _note.setRating((float) wine.getNote());
        _comment.setText(wine.getCommentaire());

        _note.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean fromUser) {
                int rating = (int) v;

                switch (rating) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        wine.setNote(rating);
                        db.update(wine);
                        break;
                }
            }

        });

        if(wine.getCommentaire() != null) {
            _comment.setFocusable(false);
            _btnComment.setVisibility(View.INVISIBLE);
        }
        else {
            _comment.setFocusable(true);
            _btnComment.setVisibility(View.VISIBLE);
        }

        _btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wine.setCommentaire(_comment.getText().toString().trim());
                db.update(wine);
                _comment.setFocusable(false);
                _btnComment.setVisibility(View.INVISIBLE);
            }
        });

        _comment.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                wine.setCommentaire(null);
                                db.update(wine);
                                _comment.setFocusable(true);
                                _comment.setText(null);
                                _btnComment.setVisibility(View.VISIBLE);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Voulez-vous supprimer le commentaire?").setPositiveButton("Oui", dialogClickListener)
                        .setNegativeButton("Non", dialogClickListener).show();
                return true;
            }
        });
    }
}
