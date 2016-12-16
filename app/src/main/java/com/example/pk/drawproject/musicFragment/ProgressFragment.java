package com.example.pk.drawproject.musicFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pk.drawproject.R;
import com.example.pk.drawproject.main.MainActivity;

import me.wangyuwei.loadingview.LoadingView;

/**
 * Created by pk on 29.11.2016.
 */
public class ProgressFragment extends Fragment {
    LoadingView loadingView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.progress_layout,container,false);
        loadingView = (LoadingView)v.findViewById(R.id.loading_view);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadingView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        loadingView.stop();
    }
}
