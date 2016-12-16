package com.example.pk.drawproject.musicFragment.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.pk.drawproject.R;
import com.example.pk.drawproject.data.VkAudioModel;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.claucookie.miniequalizerlibrary.EqualizerView;
import rm.com.longpresspopup.LongPressPopup;
import rm.com.longpresspopup.LongPressPopupBuilder;
import rm.com.longpresspopup.PopupInflaterListener;
import rm.com.longpresspopup.PopupStateListener;

/**
 * Created by pk on 17.10.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    ArrayList<VkAudioModel> audiodata;
    Context context;
    private RecyclerItemClickListener recyclerItemClickListener;
    int currentPosition = -1;

    int popupPosition;

    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public RecyclerViewAdapter(ArrayList<VkAudioModel> audiodata, Context context) {
        this.context = context;
        this.audiodata = audiodata;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupInflaterListener, PopupStateListener, View.OnLongClickListener {

        TextView popup_tv;
        TextView title;
        TextView group;
        TextView duration;
        Context context;
        EqualizerView equalizer;
        LongPressPopup popup;
        ScrollView popup_scrollView;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.title = (TextView) itemView.findViewById(R.id.titleTv);
            this.equalizer = (EqualizerView) itemView.findViewById(R.id.equalizer);
            this.group = (TextView) itemView.findViewById(R.id.groupTv);
            this.duration = (TextView) itemView.findViewById(R.id.durationTv);

            popup = new LongPressPopupBuilder(context)// A Context object for the builder constructor
                    .setPopupView(R.layout.popup, this)// The View to show when long pressed
                    .setPopupListener(this)
                    .setDismissOnLongPressStop(false)
                    .setDismissOnTouchOutside(true)
                    .setDismissOnBackPressed(true)
                    .setTarget(itemView)
                    .build();// This will give you a LongPressPopup object

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (currentPosition == -1) {
                audiodata.get(getAdapterPosition()).setChoise(true);
                currentPosition = getAdapterPosition();
                notifyItemChanged(getAdapterPosition());

                recyclerItemClickListener.onItemClickListener(getAdapterPosition());
            } else {
                audiodata.get(currentPosition).setChoise(false);
                notifyItemChanged(currentPosition);
                audiodata.get(getAdapterPosition()).setChoise(true);
                currentPosition = getAdapterPosition();
                notifyItemChanged(getAdapterPosition());

                recyclerItemClickListener.onItemClickListener(getAdapterPosition());
            }
        }

        @Override
        public void onViewInflated(@Nullable String popupTag, View root) {
            CardView cardView = (CardView) root.findViewById(R.id.cardView);
            popup_tv = (TextView) root.findViewById(R.id.popup_tv);
            popup_scrollView = (ScrollView) root.findViewById(R.id.scroll);
        }

        @Override
        public void onPopupShow(@Nullable final String popupTag) {
            if (audiodata.get(popupPosition).getLyrics_id().equals("")) {
                popup_scrollView.setVisibility(View.GONE);
            } else {
                VKRequest vkRequest = VKApi.audio().getLyrics(VKParameters.from("lyrics_id", audiodata.get(popupPosition).getLyrics_id()));
                vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        try {
                            JSONObject responce = response.json.getJSONObject("response");
                            String s = responce.getString("text");
                            Log.d("tag", " s = " + s);
                            popup_tv.setText(s);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        @Override
        public void onPopupDismiss(@Nullable String popupTag) {

        }

        @Override
        public boolean onLongClick(View v) {
            popupPosition = getAdapterPosition();
            popup.showNow();
            return true;
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TextView title = holder.title;
        final TextView group = holder.group;
        final TextView duration = holder.duration;
        final EqualizerView equalizerView = holder.equalizer;
        title.setText(audiodata.get(position).getTitle());
        group.setText(audiodata.get(position).getArtist());
        int duration_milisec = Integer.parseInt(audiodata.get(position).getDuration());
        duration.setText(duration_milisec / 60 + ":" + duration_milisec % 60);

        if (audiodata.get(position).isChoise()) {
            equalizerView.setVisibility(View.VISIBLE);
            equalizerView.animateBars();
        } else {
            Log.d("tag", position + " stop animating");
            equalizerView.stopBars();
            equalizerView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return audiodata.size();
    }

}

