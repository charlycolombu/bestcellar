package com.example.caveavinmmm.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.caveavinmmm.R;
import com.example.caveavinmmm.response.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class WineAdapter extends ArrayAdapter<WineElement> {
    private Context mContext;
    int mRessource;

    public WineAdapter(Context context, int resource, ArrayList<WineElement> wines) {
        super(context, resource, wines);
        mContext = context;
        mRessource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap photoVin = getItem(position).photoVin;
        String nomVin = getItem(position).nomVin;
        String vignoble = getItem(position).vignoble;
        String villeVin = getItem(position).villeVin;

        WineElement wine = new WineElement(photoVin, nomVin, vignoble);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mRessource, parent, false);

        CircleImageView civPhotoVin = (CircleImageView) convertView.findViewById(R.id.item_photo);
        TextView tvNomVin = (TextView) convertView.findViewById(R.id.item_nomvin);
        TextView tvVignoble = (TextView) convertView.findViewById(R.id.item_vignoble);

        civPhotoVin.setImageBitmap(photoVin);
        tvNomVin.setText(nomVin);
        tvVignoble.setText(vignoble);

        return convertView;
    }
}
