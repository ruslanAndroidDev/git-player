package com.example.pk.drawproject.view.playerBar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by pk on 28.10.2016.
 */
public class MusicPagerAdapter extends FragmentPagerAdapter {

    public MusicPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new SoundFragment();
        } else return new PlayerFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
