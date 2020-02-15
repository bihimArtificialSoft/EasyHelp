package com.example.easyhelp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.easyhelp.OtherActivities.DonationActivity;
import com.example.easyhelp.OtherActivities.HelpActivity;
import com.example.easyhelp.OtherActivities.ProfileActivity;
import com.example.easyhelp.OtherActivities.WalletActivity;
import com.example.easyhelp.R;

import life.sabujak.roundedbutton.RoundedButton;


public class HomeFragment extends Fragment {

    RoundedButton roundedButton;

    CardView profileCardView, balanceCardView, helpCardView, donationCardView;

    EditText helpButtonEdittext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        roundedButton = view.findViewById(R.id.help_button);
        profileCardView = view.findViewById(R.id.profile_card);
        balanceCardView = view.findViewById(R.id.wallet_card);
        helpCardView = view.findViewById(R.id.help_card);
        donationCardView = view.findViewById(R.id.donate_card);
        helpButtonEdittext = view.findViewById(R.id.help_edittext);


        profileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });


        balanceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), WalletActivity.class));
            }
        });

        helpCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), HelpActivity.class));
            }
        });

        donationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), DonationActivity.class));
            }
        });



        roundedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(helpButtonEdittext.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "Help is sending", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(getActivity(), helpButtonEdittext.getText().toString()+" is sending", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

}
