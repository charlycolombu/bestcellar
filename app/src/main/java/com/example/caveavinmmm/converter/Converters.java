package com.example.caveavinmmm.converter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class Converters {

    @TypeConverter
    public byte[] fromBitmap(Bitmap bitmap) {
        if(bitmap != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            return outputStream.toByteArray();
        }
        else {
            return null;
        }
    }

    @TypeConverter
    public Bitmap toBitmap(byte[] byteArray) {
        if(byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        else {
            return null;
        }
    }
}
