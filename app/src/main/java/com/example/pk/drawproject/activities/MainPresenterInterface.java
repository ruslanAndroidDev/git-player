package com.example.pk.drawproject.activities;

import com.example.pk.drawproject.ui.activies.MainActivity;
import com.example.pk.drawproject.ui.fragments.PlayerFragment;

/**
 * Created by pk on 05.11.2016.
 */
public interface MainPresenterInterface {
    void vkLoggin();

    void isLoggedIn();

    void setFragment();

    void tookMainView(MainActivity mainActivity);

    void tookPlayerFragment(PlayerFragment fragment);

    void showBar(int position);

    void buttonClick();
}
