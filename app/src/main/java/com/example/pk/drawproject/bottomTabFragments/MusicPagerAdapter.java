package com.example.pk.drawproject.bottomTabFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by pk on 28.10.2016.
 */
public class MusicPagerAdapter extends FragmentPagerAdapter {
    int music_position;

    public MusicPagerAdapter(FragmentManager fm, int position) {
        super(fm);
        this.music_position = position;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new SoundFragment(music_position);
        } else return new PlayerFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
