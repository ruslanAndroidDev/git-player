package com.example.pk.drawproject.bottomTabFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.pk.drawproject.R;

/**
 * Created by pk on 28.10.2016.
 */
public class PlayerFragment extends Fragment implements View.OnClickListener {
    ImageButton btn_repeat;
    ImageButton btn_main_btn;
    ImageButton btn_previous;
    ImageButton btn_next;
    ImageButton btn_random;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.player_fragment, container, false);
        btn_main_btn = (ImageButton) v.findViewById(R.id.bot_btn_main);
        btn_main_btn.setOnClickListener(this);

        btn_repeat = (ImageButton) v.findViewById(R.id.bot_btn_repeat);
        btn_repeat.setOnClickListener(this);

        btn_previous = (ImageButton) v.findViewById(R.id.bot_btn_previous);
        btn_previous.setOnClickListener(this);

        btn_next = (ImageButton) v.findViewById(R.id.bot_btn_next);
        btn_next.setOnClickListener(this);
        btn_random = (ImageButton) v.findViewById(R.id.bot_btn_random);
        btn_random.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bot_btn_repeat) {

        } else if (v.getId() == R.id.bot_btn_main) {
            //
        } else if (v.getId() == R.id.bot_btn_next) {
        } else if (v.getId() == R.id.bot_btn_previous) {
        } else if (v.getId() == R.id.bot_btn_random) {
        }
    }
}
