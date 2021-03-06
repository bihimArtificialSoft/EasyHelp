package com.example.easyhelp.RegistrationAndLogin.Login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.CustomDialog.CustomDialog;
import com.example.easyhelp.CustomSpinner.CustomSpinnerAdapter;
import com.example.easyhelp.RegistrationAndLogin.Login.Item.LoginAPIElements;
import com.example.easyhelp.MainActivity;
import com.example.easyhelp.RegistrationAndLogin.ForgetPassword.Activity.ForgetPasswordActivity;
import com.example.easyhelp.R;
import com.google.android.material.appbar.MaterialToolbar;

import es.dmoral.toasty.Toasty;
import life.sabujak.roundedbutton.RoundedButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity
{

    MaterialToolbar toolbar;
    Spinner spinner;
    RoundedButton loginButton;
    EditText userName;
    EditText password;
    String baseUrl;
    Retrofit retrofit;
    PlaceHolderAPI placeHolderAPI;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button forget_password;
    String[] professionName = {"Helper/General People", "Lawyer", "Journalist", "Police"};
    int[] icons = {R.drawable.ic_general, R.drawable.ic_lawyer, R.drawable.ic_journalist, R.drawable.ic_policeman};
    String selectedItem;
    CustomDialog customDialog;
    TextView loginText;
    String registrationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewByIDAll();
        customSpinnerSet();
        login();
        threadToast(this);
        Intent intent = getIntent();
        registrationText = intent.getStringExtra("login");


        if(registrationText!=null)
        {
            loginText.setText(registrationText);
        }


    }

    private void login()
    {
        baseUrl = new BaseUrl().baseUrl;
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(userName.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                {
                    Toasty.error(LoginActivity.this,"Username or password is empty", Toasty.LENGTH_SHORT, true).show();
                }

                else if (isNetWorkConnected())
                {
                    loginApiCall();
                }
                else
                {
                    Toasty.error(LoginActivity.this,"No internet connection is available", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                finish();
            }
        });

    }

    private void loginApiCall()
    {

        customDialog.showDialog();
        Log.d("CUSTOMSPINNER", "onItemSelected: "+selectedItem);
        Call<LoginAPIElements> call = placeHolderAPI.getLoginInfo(userName.getText().toString(), password.getText().toString(),"");
        call.enqueue(new Callback<LoginAPIElements>() {
            @Override
            public void onResponse(Call<LoginAPIElements> call, Response<LoginAPIElements> response)
            {
                if (!response.isSuccessful())
                {
                    customDialog.hideDialog();
                    Toasty.error(LoginActivity.this, "Error Code: " +response.code(),Toasty.LENGTH_SHORT,true).show();
                }

                LoginAPIElements loginAPIElements = response.body();

                int errorCode = loginAPIElements.getError();

                Log.d("BaalerLogin", "onResponse: "+loginAPIElements.getError()+loginAPIElements.getError_report());

                if (errorCode == 1)
                {
                    customDialog.hideDialog();
                    Toasty.error(LoginActivity.this, "Password or UserName not matched",Toasty.LENGTH_SHORT,true).show();
                }
                else if (errorCode == 0)
                {
                    preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    editor = preferences.edit();

                    editor.putString("user_name", userName.getText().toString());
                    editor.putString("password", password.getText().toString());
                    editor.putString("category", selectedItem);
                    editor.putString("id", loginAPIElements.getId());
                    editor.putString("name", loginAPIElements.getName());
                    editor.putString("user_catagory", loginAPIElements.getUser_catagory());
                    editor.putString("catagory_type", loginAPIElements.getCatagory_type());
                    editor.putString("mobile", loginAPIElements.getMobile());
                    editor.putString("address", loginAPIElements.getAddress());
                    editor.putString("image_url", loginAPIElements.getImage_url());
                    editor.putString("institute_name", loginAPIElements.getInstitute_name());
                    editor.putString("facebook_url", null);
                    editor.putBoolean("user_register", true);
                    boolean facebookExists = preferences.getBoolean("facebook_done", false);
                    if (!facebookExists)
                    {
                        editor.putBoolean("facebook_done", false);
                    }
                    editor.apply();
                    customDialog.hideDialog();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginAPIElements> call, Throwable t)
            {

            }
        });
    }


    private void findViewByIDAll()
    {
        loginButton = findViewById(R.id.login_button);
        spinner = findViewById(R.id.login_spinner);
        userName = findViewById(R.id.login_user_name);
        password = findViewById(R.id.login_password);
        forget_password = findViewById(R.id.forget_password_login);
        customDialog = new CustomDialog(this);
        loginText = findViewById(R.id.login_text);
    }

    private void customSpinnerSet()
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedItem = professionName[parent.getSelectedItemPosition()];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getApplicationContext(), icons, professionName);
        spinner.setAdapter(customSpinnerAdapter);
    }

    private void toolBarMethod(int toolbarId, String toolbarText)
    {
        toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(toolbarText);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /*
    * Data and Wifi Connectivity checks
    * Default value is false means no network connected
    */

    private boolean isNetWorkConnected()
    {
        boolean connectedOrNot = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null)
        {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
            {
                connectedOrNot = true;
            }

            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                connectedOrNot = true;
            }

        }

        else
        {
            connectedOrNot = false;
        }
        
        return connectedOrNot;
    }

    private void threadToast(Context context)
    {
        if (!isNetWorkConnected())
        {
            Thread t = new Thread() {

                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {
                            Thread.sleep(600000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {
                                    Toasty.error(context,"No net connection is available", Toasty.LENGTH_SHORT, true).show();
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };


            t.start();
        }
    }


}
