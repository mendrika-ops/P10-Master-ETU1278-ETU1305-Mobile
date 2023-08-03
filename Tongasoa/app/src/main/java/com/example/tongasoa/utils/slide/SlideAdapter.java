package com.example.tongasoa.utils.slide;

import android.content.Context;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.tongasoa.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

    private List<SlideItem> slideItems;
    private ViewPager2 viewPage;

    private Context ctx;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            slideItems.addAll(slideItems);
            notifyDataSetChanged();
        }
    };

    public SlideAdapter(List<SlideItem> slideItems, ViewPager2 viewPage) {
        this.slideItems = slideItems;
        this.viewPage = viewPage;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        return new SlideViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.slide_item_container,
                    parent,
                    false
            )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.setContenu(slideItems.get(position), holder);
        if(position == slideItems.size()-2) {
            viewPage.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }


    class SlideViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageSilde);
        }

        void setContenu(SlideItem slideItem, SlideViewHolder holder) {
            if(slideItem.getUrl() == null){
                // If you want to display image from ressource
                imageView.setImageResource(slideItem.getImage());
            } else {
                // If you want to display image from internet you can put code here using glide or picaso
                Glide.with(ctx).load(slideItem.getUrl()).into(holder.imageView);
            }
        }
    }
}
