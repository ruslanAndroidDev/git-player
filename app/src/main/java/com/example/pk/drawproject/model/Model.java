package com.example.pk.drawproject.model;

import com.example.pk.drawproject.ui.bottomBar.ParseTask;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

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
                for (int i = 0; i < data.size(); i++) {
                    songUrl.add(data.get(i).getUrl());
                }
                songLoadedCallBack.onSongLoadSucces(songUrl);
            }
        });
        t.start();
    }


    private void sendVkReqest() {
        final VKRequest request = VKApi.audio().get(VKParameters.from(VKApiConst.COUNT, 500));
        final ParseTask parse = new ParseTask(myсallBack);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                parse.execute(response.json);
            }
        });
    }

}
