package com.example.pk.drawproject.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pk.drawproject.ui.MainActivity;

/**
 * Created by pk on 16.10.2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new MusicFragment();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
