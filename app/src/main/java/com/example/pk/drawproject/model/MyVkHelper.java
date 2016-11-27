package com.example.pk.drawproject.model;

import com.vk.sdk.VKSdk;

import java.util.ArrayList;

/**
 * Created by pk on 27.11.2016.
 */
public class MyVkHelper {
    public ArrayList<VkAudio> searchAudio(String title){
        ArrayList<VkAudio> data = new ArrayList<>();
        data.add(new VkAudio("Title","sgfsg","artist","45"));
        data.add(new VkAudio("Title1","sgfsg","artist1","45"));
        data.add(new VkAudio("Title2","sgfsg","artis2t","25"));
        data.add(new VkAudio("Titl3","sgfsg","artist3","43"));
        data.add(new VkAudio("Title4","sgfsg","artist4","85"));
        data.add(new VkAudio("Title5","sgfsg","artist5","452"));
        data.add(new VkAudio("Title6","sgfsg","artist6","445"));
        data.add(new VkAudio("Title7","sgfsg","artist7","5"));
        return data;
    }
}
