package com.example.pk.drawproject.data;

import java.util.ArrayList;

/**
 * Created by pk on 06.11.2016.
 */
public interface PlayerInterfaces {

    interface DataLoadedCallBack {
        void onDataLoadSucces(ArrayList<VkAudioModel> vkAudios);
    }
    interface FragmentAction{
        void showMyAudioList();
        void showSearchAudioList(String text);
    }
}
