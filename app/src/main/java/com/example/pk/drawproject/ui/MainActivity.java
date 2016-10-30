package com.example.pk.drawproject.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.pk.drawproject.R;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewPager;
    ImageButton searchButton;
    private String[] scope = new String[]{VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL, VKScope.AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        if (!VKSdk.isLoggedIn()) {
            VKSdk.login(this, scope);
            showUI();
        } else {
            showUI();
        }
    }

    @Override
    public void onClick(View v) {

    }
    private void showUI(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }
}
