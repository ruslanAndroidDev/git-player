package com.example.pk.drawproject.ui.bottomBar;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pk.drawproject.model.ModelInterface;
import com.example.pk.drawproject.model.VkAudio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pk on 29.11.2016.
 */
public class ParseTask extends AsyncTask<JSONObject, Void, ArrayList<VkAudio>> {

    ArrayList<VkAudio> data;
    private ModelInterface.DataLoadedCallBack myсallBack;

    public ParseTask(ModelInterface.DataLoadedCallBack myсallBack) {
        this.myсallBack = myсallBack;
    }

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
        Log.d("tag", "sizeJson" + json.length());
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
