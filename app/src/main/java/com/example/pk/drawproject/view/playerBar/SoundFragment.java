package com.example.pk.drawproject.view.playerBar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pk.drawproject.R;
import com.example.pk.drawproject.musicFragment.MusicPresenter;

/**
 * Created by pk on 28.10.2016.
 */
@SuppressLint("ValidFragment")
public class SoundFragment extends Fragment implements PlayerBarInterface {
    View v;

    TextView bot_tab_title;
    TextView bot_tab_artist;
    ImageButton bot_tab_btn;

    PlayerBarPresenter playerBarPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.bottom_tab, container, false);
        bot_tab_title = (TextView) v.findViewById(R.id.bot_tab_title);
        bot_tab_artist = (TextView) v.findViewById(R.id.bot_tab_artist);
        bot_tab_btn = (ImageButton) v.findViewById(R.id.bot_tab_btn);
        MusicPresenter.registerPlayerBar(this);

        bot_tab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerBarPresenter.buttonClick();
            }
        });
        return v;
    }

    @Override
    public void filingField(String title,String artist) {
        bot_tab_title.setText(title);
        bot_tab_artist.setText(artist);
    }

    @Override
    public void setMainBtnOnPause() {
        bot_tab_btn.setImageResource(R.drawable.ic_pause);
    }

    @Override
    public void setMainBtnOnPlay() {
        bot_tab_btn.setImageResource(R.drawable.ic_play);
    }
}
