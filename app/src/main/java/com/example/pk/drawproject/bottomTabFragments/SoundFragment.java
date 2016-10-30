package com.example.pk.drawproject.bottomTabFragments;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pk.drawproject.MediaPlayerHelper;
import com.example.pk.drawproject.R;
import com.example.pk.drawproject.myInterfaces.AdapterCallback;
import com.example.pk.drawproject.ui.MusicFragment;
import com.example.pk.drawproject.ui.RecyclerViewAdapter;

/**
 * Created by pk on 28.10.2016.
 */
@SuppressLint("ValidFragment")
public class SoundFragment extends Fragment implements AdapterCallback {
    MediaPlayer player;
    View v;
    int position;

    public SoundFragment(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.bottom_tab, container, false);
        RecyclerViewAdapter.registerForCallbeck(SoundFragment.this);
        initField();
        player = MediaPlayerHelper.getPlayer();
        filingField(position);
        bot_tab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()) {
                    player.pause();
                    bot_tab_btn.setImageResource(R.drawable.ic_play);
                } else {
                    player.start();
                    bot_tab_btn.setImageResource(R.drawable.ic_pause);
                }
            }
        });
        return v;
    }

    @Override
    public void onRecyclerItemClick(int position) {
        Log.d("tag", position + "call");
        filingField(position);
    }

    // Заповнення полів
    private void filingField(int position) {
        bot_tab_title.setText(MusicFragment.data.get(position).getTitle());
        bot_tab_artist.setText(MusicFragment.data.get(position).getArtist());
        bot_tab_btn.setImageResource(R.drawable.ic_pause);
    }

    TextView bot_tab_title;
    TextView bot_tab_artist;
    ImageButton bot_tab_btn;

    private void initField() {
        bot_tab_title = (TextView) v.findViewById(R.id.bot_tab_title);
        bot_tab_artist = (TextView) v.findViewById(R.id.bot_tab_artist);
        bot_tab_btn = (ImageButton) v.findViewById(R.id.bot_tab_btn);
    }
}
