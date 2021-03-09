package com.example.caveavinmmm;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.caveavinmmm.api.ImaggaClient;
import com.example.caveavinmmm.api.UploadApis;
import com.example.caveavinmmm.data.UserDatabase;
import com.example.caveavinmmm.data.WineDAO;
import com.example.caveavinmmm.fragments.AccueilFragment;
import com.example.caveavinmmm.fragments.MapFragment;
import com.example.caveavinmmm.fragments.ProfileFragment;
import com.example.caveavinmmm.model.Wine;
import com.example.caveavinmmm.response.ImaggaResponse;
import com.example.caveavinmmm.response.Text;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class MenuActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private ImaggaResponse imaggaResponse;
    private ContentValues values;
    private Uri fileUri;
    private AlertDialog dialog;

    WineDAO wineDAO;
    UserDatabase dataBase;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_accueil:
                        selectedFragment = new AccueilFragment();
                        break;
                    case R.id.nav_map:
                        selectedFragment = new MapFragment();
                        break;
                    case R.id.nav_profil:
                        selectedFragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AccueilFragment()).commit();
        }

        FloatingActionButton fab = findViewById(R.id.btn_photo);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, "Bouton photo appuyé", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MenuActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CODE);
                // create Intent to take a picture and return control to the calling application
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                startActivityForResult(intent, CAMERA_REQUEST);
                Log.e("onClick", "ok");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult", "OK");
        super.onActivityResult(requestCode, resultCode, data);

        int permission = ActivityCompat.checkSelfPermission(MenuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    MenuActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            String imageurl = getRealPathFromURI(fileUri);

            File finalFile = new File(fileUri.getPath());
            Toast.makeText(MenuActivity.this, finalFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            
            callImagga(finalFile);
        }
    }

    private void callImagga(File file) {
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),file);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", file.getName(),reqBody);

        UploadApis api = ImaggaClient.getApiServices();
        Call<ImaggaResponse> upload = api.uploadImage("Basic YWNjXzZhNjZhNGUxNTM5Mzc3NzpmZjY3MGMwYTVjN2UyNTBjNDBiYjczMTEwMzZjYTRkMA==", partImage);

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(MenuActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Chargement....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // show it
        progressDoalog.show();

        upload.enqueue(new Callback<ImaggaResponse>() {
            @Override
            public void onResponse(Call<ImaggaResponse> call, Response<ImaggaResponse> response) {
                imaggaResponse = response.body();
                progressDoalog.dismiss();
                /*for(Text text : imaggaResponse.getResult().getText()) {
                    Log.d("RETRO", text.getData());
                    Toast.makeText(MenuActivity.this, text.getData(), Toast.LENGTH_LONG).show();
                }*/
                findProductWithImagga(imaggaResponse);
            }

            @Override
            public void onFailure(Call<ImaggaResponse> call, Throwable t) {
                progressDoalog.dismiss();
                Log.d("RETRO", t.toString());
            }
        });
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public String getRealPathFromURIBis(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void findProductWithImagga(ImaggaResponse response) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        createNewProductDialog();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Produit non trouvé. Voulez-vous le créer ?").setPositiveButton("Oui", dialogClickListener)
                .setNegativeButton("Non", dialogClickListener).show();
    }

    public void createNewProductDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View productPopupView = getLayoutInflater().inflate(R.layout.popup_product, null);

        EditText vignoble = (EditText) productPopupView.findViewById(R.id.input_vignoble);
        EditText nomVin = (EditText) productPopupView.findViewById(R.id.input_nomvin);
        EditText typeVin = (EditText) productPopupView.findViewById(R.id.input_type);

        Button cancel = (Button) productPopupView.findViewById(R.id.btn_annuler);
        Button ajouter = (Button) productPopupView.findViewById(R.id.btn_ajouter);

        builder.setView(productPopupView);
        dialog = builder.create();
        dialog.show();

        dataBase = UserDatabase.getInstance(this);
        wineDAO = dataBase.getWineDao();

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wine wine = new Wine(vignoble.getText().toString().trim(), nomVin.getText().toString().trim(), typeVin.getText().toString().trim());
                wineDAO.insert(wine);
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
