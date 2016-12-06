package com.example.pk.drawproject;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pk.drawproject.model.OnDataLoadInterface;
import com.example.pk.drawproject.model.VkAudioModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pk on 29.11.2016.
 */
public class ParseTask extends AsyncTask<JSONObject, Void, ArrayList<VkAudioModel>> {

    ArrayList<VkAudioModel> data;
    private OnDataLoadInterface.DataLoadedCallBack myсallBack;

    public ParseTask(OnDataLoadInterface.DataLoadedCallBack myсallBack) {
        this.myсallBack = myсallBack;
    }

    @Override
    protected ArrayList<VkAudioModel> doInBackground(JSONObject... params) {
        return parseJson(params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<VkAudioModel> vkAudios) {
        myсallBack.onDataLoadSucces(vkAudios);
    }

    private ArrayList<VkAudioModel> parseJson(JSONObject json) {
        data = new ArrayList<>();
        Log.d("tag", "sizeJson" + json.length());
        try {
            JSONObject responce = json.getJSONObject("response");
            JSONArray items = responce.getJSONArray("items");
            for (int i = items.length() - 1; i >= 0; i--) {
                JSONObject music = items.getJSONObject(i);
                VkAudioModel vkAudio = new VkAudioModel(music.getString("title"), music.getString("url"), music.getString("artist"), music.getString("duration"));
                data.add(0, vkAudio);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
