
package com.buckleit.imhotel.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.buckleit.imhotel.requestModel.LoginDetails;
import com.buckleit.imhotel.responseModel.ApiResponse;
import com.buckleit.imhotel.rest.ApiClient;

import om.buckleit.imhotel.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button signInButton;
    EditText etEmail,etPassword;

    ProgressDialog progress;
    Context context;
    String emailId = "", password = "";
    private Call<ApiResponse> call;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        init();
        registerClickListerners();


    }


    public void  init(){
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        signInButton = (Button) findViewById(R.id.signInButton);

    }


    public void registerClickListerners() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        emailId = etEmail.getText().toString();
                        password = etPassword.getText().toString();
                        loginApiCall();
            }
        });
    }

    public void loginApiCall() {


        progress = new ProgressDialog(context);
        progress.setMessage("Please Wait. ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        LoginDetails loginDetails = new LoginDetails(emailId, password);

        call = ApiClient.getInstance().loginUser(loginDetails);


        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.i("LOGIN SUCCESS", response.body().toString());
                if (response.body().getCode() == 200) {



                    if(true){
                        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                        startActivity(intent);
                        finish();
                    }


//                    if(response.body().getData().getAccess_Key()!=null){
//
//                        Data d = response.body().getData();
//                        String access = d.getAccess_Key();
//                         Log.i("AAAAAAAAAAA : ",access);

//                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//                        SharedPreferences.Editor editor = preferences.edit();
//                        editor.putString("access_key",access);
//                        editor.apply();
//
//                        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }

                }
               else{
                    progress.dismiss();
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                    dlgAlert.setMessage("wrong password or username");
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
            public void onFailure(Call<ApiResponse> call, Throwable t) {
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
