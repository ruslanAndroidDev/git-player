package com.example.pk.drawproject.model;

import android.os.AsyncTask;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pk on 06.11.2016.
 */
public class Model implements ModelInterface {
    static ArrayList<VkAudio> data;
    static ArrayList<String> songUrl;
    DataLoadedCallBack myсallBack;


    @Override
    public void getVkSoundListWithListener(DataLoadedCallBack callBack) {
        myсallBack = callBack;
        if (data != null) {
            myсallBack.onDataLoadSucces(data);
        } else {
            sendVkReqest();
        }
    }

    @Override
    public void getSongWithListener(final SongLoadedCallBack songLoadedCallBack) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                songUrl = new ArrayList<>();
                for (int i=0;i<data.size();i++){
                    songUrl.add(data.get(i).getUrl());
                }
                songLoadedCallBack.onSongLoadSucces(songUrl);
            }
        });
        t.start();
    }


    private void sendVkReqest() {
        final VKRequest request = VKApi.audio().get();
        final ParseTask parse = new ParseTask();
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                parse.execute(response.json);
            }
        });
    }


    private class ParseTask extends AsyncTask<JSONObject, Void, ArrayList<VkAudio>> {

        @Override
        protected ArrayList<VkAudio> doInBackground(JSONObject... params) {
            return parseJson(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<VkAudio> vkAudios) {
            myсallBack.onDataLoadSucces(vkAudios);
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
