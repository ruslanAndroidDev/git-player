package com.example.pk.drawproject.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pk.drawproject.R;
import com.example.pk.drawproject.model.VkAudio;
import com.example.pk.drawproject.myInterfaces.AdapterCallback;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pk on 28.10.2016.
 */
public class MusicFragment extends Fragment implements AdapterCallback {
    RecyclerView music_Recycler_View;
    public static ArrayList<VkAudio> data;
    Context context;
    ViewPager musicPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.music_fragment, container, false);
        context = v.getContext();
        music_Recycler_View = (RecyclerView) v.findViewById(R.id.musicRecyclerView);
        music_Recycler_View.setLayoutManager(new LinearLayoutManager(v.getContext()));
        musicPager = (ViewPager) v.findViewById(R.id.musicviewPager);
        musicPager.setVisibility(View.GONE);
        final VKRequest request = VKApi.audio().get();
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                ParseTask parse = new ParseTask();
                parse.execute(response.json);
            }
        });
        return v;
    }


    @Override
    public void onRecyclerItemClick(int position) {

        musicPager.setAdapter(new MusicPagerAdapter(getFragmentManager(), position));
        musicPager.setVisibility(View.VISIBLE);
    }

    private class ParseTask extends AsyncTask<JSONObject, Void, ArrayList<VkAudio>> {

        @Override
        protected ArrayList<VkAudio> doInBackground(JSONObject... params) {
            return parseJson(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<VkAudio> vkAudios) {
            music_Recycler_View.setAdapter(new RecyclerViewAdapter(vkAudios, context, MusicFragment.this));
            Log.d("tag", "setAdapter");
        }

    }

    private ArrayList<VkAudio> parseJson(JSONObject json) {
        data = new ArrayList<>();
        try {
            JSONObject responce = json.getJSONObject("response");
            JSONArray items = responce.getJSONArray("items");
            for (int i = items.length() - 1; i >= 0; i--) {
                JSONObject music = items.getJSONObject(i);
                VkAudio vkAudio = new VkAudio(music.getString("title"), music.getString("url"), music.getString("artist"), music.getString("duration"));
                data.add(0, vkAudio);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

}