package com.example.caveavinmmm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.caveavinmmm.api.ImaggaClient;
import com.example.caveavinmmm.api.UploadApis;
import com.example.caveavinmmm.fragments.AccueilFragment;
import com.example.caveavinmmm.fragments.MapFragment;
import com.example.caveavinmmm.fragments.ProfileFragment;
import com.example.caveavinmmm.fragments.RechercheFragment;
import com.example.caveavinmmm.fragments.WishlistFragment;
import com.example.caveavinmmm.response.ImaggaResponse;
import com.example.caveavinmmm.response.Text;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MenuActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    ImaggaResponse imaggaResponse;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_accueil:
                        selectedFragment = new AccueilFragment();
                        break;
                    case R.id.nav_recherche:
                        selectedFragment = new RechercheFragment();
                        break;
                    case R.id.nav_map:
                        selectedFragment = new MapFragment();
                        break;
                    case R.id.nav_wishlist:
                        selectedFragment = new WishlistFragment();
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
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
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
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //if you want to encode the image into base64
            if (imageBitmap!=null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            }

            Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            Toast.makeText(MenuActivity.this, finalFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();

            //File finalFile = new File("sdcard/DCIM/Camera/20200904_152621.jpg");
            Date lastModDate = new Date(finalFile.lastModified());

            Toast.makeText(MenuActivity.this, lastModDate.toString(), Toast.LENGTH_SHORT).show();
            callImagga(finalFile);
        }
    }

    private void callImagga(File file) {
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),file);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", file.getName(),reqBody);

        UploadApis api = ImaggaClient.getApiServices();
        Call<ImaggaResponse> upload = api.uploadImage("Basic YWNjXzZhNjZhNGUxNTM5Mzc3NzpmZjY3MGMwYTVjN2UyNTBjNDBiYjczMTEwMzZjYTRkMA==", partImage);
        upload.enqueue(new Callback<ImaggaResponse>() {
            @Override
            public void onResponse(Call<ImaggaResponse> call, Response<ImaggaResponse> response) {
                imaggaResponse = response.body();
                for(Text text : imaggaResponse.getResult().getText()) {
                    Log.d("RETRO", text.getData());
                }
            }

            @Override
            public void onFailure(Call<ImaggaResponse> call, Throwable t) {
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
}
