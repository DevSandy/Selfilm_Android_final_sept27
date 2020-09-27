package com.accelstackace.selfilmindia.Prime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.accelstackace.selfilmindia.R;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    public SliderAdapterExample(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        final SliderItem sliderItem = mSliderItems.get(position);
        viewHolder.textViewDescription.setText(sliderItem.getMoviename());
        viewHolder.views.setText("Views : "+sliderItem.getViews());
        viewHolder.language.setText(sliderItem.getLanguage());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImageUrl())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ottplayer.class);
                intent.putExtra("Movieid",sliderItem.getMovieId());
                intent.putExtra("MovieName",sliderItem.getMoviename());
                intent.putExtra("MovieUrl",sliderItem.getMoviurl());
                intent.putExtra("Views",sliderItem.getViews());
                intent.putExtra("Description",sliderItem.getDescription());
                intent.putExtra("Language",sliderItem.getLanguage());
                intent.putExtra("LaunchDate",sliderItem.getLauncheddate());
                intent.putExtra("MovieType",sliderItem.getMovietype());
                intent.putExtra("MovieThum",sliderItem.getImageUrl());
                intent.putExtra("Created",sliderItem.getMoviecreated());
                intent.putExtra("HeroName",sliderItem.getMaleActor());
                intent.putExtra("HeroThum",sliderItem.getMaleActorThumb());
                intent.putExtra("HeroineName",sliderItem.getfeMaleActor());
                intent.putExtra("HeroineThum",sliderItem.getfeMaleActorThumb());
                intent.putExtra("DirectorName",sliderItem.getDirector());
                intent.putExtra("DirectorThum",sliderItem.getDirectorThumb());
                intent.putExtra("ProducerName",sliderItem.getProducername());
                intent.putExtra("ProducerThum",sliderItem.getProducerthumb());
                intent.putExtra("MusicName",sliderItem.getMusic());
                intent.putExtra("MusicThum",sliderItem.getMusicThumb());
                intent.putExtra("OtherCast",sliderItem.getothercast());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription,views,language;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            views = itemView.findViewById(R.id.views);
            language = itemView.findViewById(R.id.language);
            this.itemView = itemView;
        }
    }

}