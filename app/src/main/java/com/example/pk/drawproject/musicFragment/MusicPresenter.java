package com.example.pk.drawproject.musicFragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.example.pk.drawproject.model.VkAudio;
import com.example.pk.drawproject.musicFragment.recyclerView.RecyclerItemClickListener;
import com.example.pk.drawproject.musicFragment.recyclerView.RecyclerViewAdapter;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public class MusicPresenter implements MusicPresenterInterface, RecyclerItemClickListener {
    MusicFragment fragment;
    RecyclerViewAdapter adapter;
    MediaPlayer mediaPlayer;
    int prevPosition;
    ArrayList<VkAudio> data;

    public MusicPresenter(MusicFragment fragment) {
        this.fragment = fragment;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }


    @Override
    public void vkGetAudio() {
        final VKRequest request = VKApi.audio().get();
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                ParseTask parse = new ParseTask();
                parse.execute(response.json);
            }
        });
    }

    @Override
    public void setAdapter(ArrayList<VkAudio> vkAudios) {
        adapter = new RecyclerViewAdapter(vkAudios, fragment.getContext());
        adapter.setRecyclerItemClickListener(this);
        fragment.setAdapter(adapter);
    }

    @Override
    public void playSound(int position) {
        if (prevPosition == position) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        } else {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(data.get(position).getUrl());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }
        setPlayerBar(position);
        prevPosition=position;
    }

    @Override
    public void setPlayerBar(int position) {
        fragment.setPlayerBar(position);
    }

    @Override
    public void onItemClickListener(int position) {
        playSound(position);
    }

    private class ParseTask extends AsyncTask<JSONObject, Void, ArrayList<VkAudio>> {

        @Override
        protected ArrayList<VkAudio> doInBackground(JSONObject... params) {
            return parseJson(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<VkAudio> vkAudios) {
            setAdapter(vkAudios);
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
}
