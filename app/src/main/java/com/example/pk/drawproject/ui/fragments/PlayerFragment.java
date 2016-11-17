package com.example.pk.drawproject.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pk.drawproject.main.MainPresenterImpl;
import com.example.pk.drawproject.R;

/**
 * Created by pk on 11.11.2016.
 */
public class PlayerFragment extends Fragment implements View.OnClickListener {
    TextView titleTv;
    TextView artistTv;
    ImageButton bot_tab_btn;
    MainPresenterImpl presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_tab,container,false);

        titleTv = (TextView)v.findViewById(R.id.bot_tab_title);
        artistTv = (TextView)v.findViewById(R.id.bot_tab_artist);

        bot_tab_btn = (ImageButton)v.findViewById(R.id.bot_tab_btn);
        bot_tab_btn.setOnClickListener(this);

      //  presenter = new MainPresenterImpl();
      //  presenter.attachPlayerFragment(this);
        return v;
    }
    public void fillingField(String title,String artist){
        titleTv.setText(title);
        artistTv.setText(artist);
    }

    @Override
    public void onClick(View v) {
        presenter.barButtonClick();
    }
    public void setButtonOnPause(){
        bot_tab_btn.setImageResource(R.drawable.ic_pause_white_48dp);
    }
    public void setButtonOnStart(){
        bot_tab_btn.setImageResource(R.drawable.ic_play);
    }

}
