package com.example.pk.drawproject.model;

import com.example.pk.drawproject.ParseTask;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

/**
 * Created by pk on 27.11.2016.
 */
public class MyVkHelper {
    public final int COUNT_SEARCH_ITEM = 50;


    public void searchAudio(String soundTitle, final OnDataLoadInterface.DataLoadedCallBack callBack) {
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

    public void getVkSoundListWithListener(final OnDataLoadInterface.DataLoadedCallBack callBack) {
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
}
