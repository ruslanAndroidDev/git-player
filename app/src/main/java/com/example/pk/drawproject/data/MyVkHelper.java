package com.example.pk.drawproject.data;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.pk.drawproject.PlayerService;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pk on 27.11.2016.
 */
public class MyVkHelper {
    public final int COUNT_SEARCH_ITEM = 50;


    public void searchAudio(String soundTitle, final PlayerInterfaces.DataLoadedCallBack callBack) {
        final VKRequest request = VKApi.audio().search(VKParameters.from(VKApiConst.Q, soundTitle, VKApiConst.COUNT, COUNT_SEARCH_ITEM));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                ParseTask parseTask = new ParseTask(callBack);
                parseTask.execute(response.json);
            }
        });
    }
    public void divideData(ArrayList<VkAudioModel> vkAudios,Context context){
        DivideDataTask divideDataTask = new DivideDataTask(context);
        divideDataTask.execute(vkAudios);

    }

    public void getMySoundListWithListener(final PlayerInterfaces.DataLoadedCallBack callBack) {
        final VKRequest request = VKApi.audio().get(VKParameters.from(VKApiConst.COUNT, 500));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                final ParseTask parse = new ParseTask(callBack);
                parse.execute(response.json);
            }
        });
    }

    public class ParseTask extends AsyncTask<JSONObject, Void, ArrayList<VkAudioModel>> {

        ArrayList<VkAudioModel> data;
        private PlayerInterfaces.DataLoadedCallBack myсallBack;

        public ParseTask(PlayerInterfaces.DataLoadedCallBack myсallBack) {
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


    public class DivideDataTask extends AsyncTask<ArrayList<VkAudioModel>, Void, Void> {
        final ArrayList<String> url = new ArrayList<>();
        final ArrayList<String> title = new ArrayList<>();
        Context context;

        public DivideDataTask(Context context) {
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
            Intent intent = new Intent(context, PlayerService.class);
            intent.putStringArrayListExtra("url", url);
            intent.putStringArrayListExtra("title", title);
            intent.setAction(PlayerService.SET_DATA);
            context.startService(intent);
        }
    }

}
