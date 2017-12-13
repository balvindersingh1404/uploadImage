
package com.buckleit.imhotel.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.buckleit.imhotel.responseModel.LogoutApiResponse;
import com.buckleit.imhotel.responseModel.UpdateApiResponse;
import com.buckleit.imhotel.rest.ApiClient;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import om.buckleit.imhotel.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingActivity extends AppCompatActivity {
    Bitmap bitmap;

    Button logoutButton;
   public static String access_key;
    private Call<LogoutApiResponse> call;

    private Call<UpdateApiResponse> updateCall;
    private File mFile=null;


    Button GetImageFromGalleryButton, UploadImageOnServerButton;

    ImageView ShowSelectedImage;

    ProgressDialog progress;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        context=this;

        init();
        registerListener();


//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        access_key = preferences.getString("access_key", "");


    }

///////////////////////////////////////////////////////////////////////////////////////////////////
    public void init(){
        logoutButton = (Button) findViewById(R.id.logoutButton);
        GetImageFromGalleryButton = (Button)findViewById(R.id.button);
        UploadImageOnServerButton = (Button)findViewById(R.id.button2);
        ShowSelectedImage = (ImageView)findViewById(R.id.imageView);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void registerListener(){

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutApiCall();
            }
        });

        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });

        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImageFile(mFile);

            }
        });

    }



    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);
        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ShowSelectedImage.setImageBitmap(bitmap);
                // Get the Image from data
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String mediaPath = cursor.getString(columnIndex);
                mFile = new File(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                cursor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void uploadImageFile(File file){
        if(mFile==null)
            return;
        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait. ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();


        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        updateCall = ApiClient.getInstance().uploadFile(fileToUpload);
        updateCall.enqueue(new Callback<UpdateApiResponse>() {
            @Override
            public void onResponse(Call<UpdateApiResponse> call, Response<UpdateApiResponse> response) {
                progress.dismiss();
                if(response.body().getCode()==200){
                    Toast.makeText(LandingActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateApiResponse> call, Throwable t) {
                Toast.makeText(LandingActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();

            }
        });
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void logoutApiCall() {

        progress = new ProgressDialog(context);
        progress.setMessage("Please Wait. ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        call = ApiClient.getInstance().logout(access_key);
        call.enqueue(new Callback<LogoutApiResponse>() {
            @Override
            public void onResponse(Call<LogoutApiResponse> call, Response<LogoutApiResponse> response) {
                Log.i("LOGIN SUCCESS", response.body().toString());
                if (response.body().getCode() == 200) {

//                    if(response.body().getData().getAccess_Key()!=null){
//
//                        Data d = response.body().getData();
//                        String access = d.getAccess_Key();
//                        Log.i("AAAAAAAAAAA : ",access);
//
//
//                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//                        SharedPreferences.Editor editor = preferences.edit();
//                        editor.putString("access_key",access);
//                        editor.apply();
//
                       Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                       startActivity(intent);
                        finish();
//                    }
                }
                else{
                    progress.dismiss();
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                    dlgAlert.setMessage("Logout not possible");
                    dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                }
            }

            @Override
            public void onFailure(Call<LogoutApiResponse    > call, Throwable t) {
                Log.i("API RESPONSE FAIL", t.toString());
                progress.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("Please check your Internet Connection.");
                dlgAlert.setTitle("Error Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
            }
        });

    }
}
