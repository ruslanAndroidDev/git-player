package com.example.pk.drawproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.pk.drawproject.data.VkAudioModel;

import java.util.ArrayList;

/**
 * Created by pk on 06.12.2016.
 */
public class DivideData extends AsyncTask<ArrayList<VkAudioModel>, Void, Void> {
    final ArrayList<String> url = new ArrayList<>();
    final ArrayList<String> title = new ArrayList<>();
    Context context;

    public DivideData(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(ArrayList<VkAudioModel>... params) {
        for (int i = 0; i < params[0].size(); i++) {
            url.add(params[0].get(i).getUrl());
            title.add(params[0].get(i).getTitle());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            Intent intent = new Intent(context, PlayerService.class);
            intent.putStringArrayListExtra("url", url);
            intent.putStringArrayListExtra("title", title);
            intent.setAction(PlayerService.SET_DATA);
            context.startService(intent);
        }catch (NullPointerException e){}
    }
}
