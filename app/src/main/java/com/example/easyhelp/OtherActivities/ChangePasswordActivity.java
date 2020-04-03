package com.example.easyhelp.OtherActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.ChangePasswordThings.ChangePasswordItems;
import com.example.easyhelp.CustomSpinner.CustomSpinnerAdapter;
import com.example.easyhelp.R;
import com.google.android.material.appbar.MaterialToolbar;

import es.dmoral.toasty.Toasty;
import life.sabujak.roundedbutton.RoundedButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    String baseUrl = new BaseUrl().baseUrl;
    Retrofit retrofit;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    PlaceHolderAPI placeHolderAPI;
    EditText editTextUserName, editTextOldPassword, editTextConfirmOldPassword, editTextNewPassword, editTextConfirmNewPassword;
    RoundedButton buttonSubmit;
    Spinner spinner;
    String[] professionName = {"Helper/General People", "Lawyer", "Journalist", "Police"};
    int[] icons = {R.drawable.ic_general, R.drawable.ic_lawyer, R.drawable.ic_journalist, R.drawable.ic_policeman};
    String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        toolBarMethod(R.id.toolbar_change_password,"Change Password");
        findViewByAll();
        customSpinnerSet();
        changePassword();

    }

    private void changePassword()
    {
        baseUrl = new BaseUrl().baseUrl;
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String username = editTextUserName.getText().toString();
                String password = editTextOldPassword.getText().toString();
                String oldPassword = editTextOldPassword.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String againPassword = editTextConfirmNewPassword.getText().toString();

                Call<ChangePasswordItems> call = placeHolderAPI.getChangePasswordInfo(username,password,oldPassword,newPassword,againPassword);
                call.enqueue(new Callback<ChangePasswordItems>() {
                    @Override
                    public void onResponse(Call<ChangePasswordItems> call, Response<ChangePasswordItems> response)
                    {
                        if (!response.isSuccessful())
                        {
                            Toasty.error(ChangePasswordActivity.this, "Error Code: " +response.code(),Toasty.LENGTH_SHORT,true).show();
                        }

                        ChangePasswordItems changePasswordItems = response.body();

                        int errorCode = changePasswordItems.getError();

                        if (errorCode == 0)
                        {
                            preferences = PreferenceManager.getDefaultSharedPreferences(ChangePasswordActivity.this);
                            editor = preferences.edit();
                            editor.putString("password", password);
                            editor.apply();
                            Toasty.success(ChangePasswordActivity.this, "Password Successfully Changed", Toasty.LENGTH_SHORT, true).show();
                        }

                        else if (errorCode == 1)
                        {
                            Toasty.error(ChangePasswordActivity.this, "Could not change the password", Toasty.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePasswordItems> call, Throwable t)
                    {

                    }
                });
            }
        });
    }

    private void customSpinnerSet()
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedItem = professionName[parent.getSelectedItemPosition()];
                Log.d("CUSTOMSPINNER", "onItemSelected: "+selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getApplicationContext(), icons, professionName);
        spinner.setAdapter(customSpinnerAdapter);
    }

    private void findViewByAll()
    {
        editTextOldPassword = findViewById(R.id.change_password_password);
        editTextConfirmOldPassword = findViewById(R.id.change_password_old_password);
        editTextNewPassword = findViewById(R.id.change_password_new_password);
        editTextConfirmNewPassword = findViewById(R.id.change_password_again_password);
        spinner = findViewById(R.id.change_password_spinner);
        buttonSubmit = findViewById(R.id.change_password_button);
        editTextUserName = findViewById(R.id.change_password_user_name);
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
}