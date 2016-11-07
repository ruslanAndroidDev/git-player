package com.example.pk.drawproject.model;

import android.os.AsyncTask;

import com.example.pk.drawproject.musicFragment.MusicPresenter;
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
    MusicPresenter musicPresenter;
    DataLoadedCallBack myсallBack;

    public Model(MusicPresenter presenter) {
        this.musicPresenter = presenter;
    }

    @Override
    public void getVkSoundListWithListener(DataLoadedCallBack callBack) {
        myсallBack = callBack;
        if (data != null) {
            myсallBack.onDataLoadSucces(data);
        } else {
            sendVkReqest();
        }
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
