package com.example.tongasoa.utils.slide;

import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tongasoa.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

    private List<SlideItem> slideItems;
    private ViewPager2 viewPage;
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
        if(viewType == 1 ) {
            return new SlideViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.slide_item_video,
                            parent,
                            false
                    ), viewType
            );
        } else {
            return new SlideViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false
                ), viewType
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.setContenu(slideItems.get(position));
        if(position == slideItems.size()-2) {
            viewPage.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        SlideItem slideItem = slideItems.get(position);
        if(slideItem.getVideo() != null) {
            return 1;
        } else {
            return 0;
        }
    }

    class SlideViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        private WebView webView;
        SlideViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if(viewType == 1 ) {
                this.webView = itemView.findViewById(R.id.webView);
            } else {
                this.imageView = itemView.findViewById(R.id.imageSilde);
            }
        }

        void setContenu(SlideItem slideItem) {
            if(slideItem.getVideo() != null ){
                // iframe is compnent HMTL on the youtube share option
                String iframe = "<iframe width=\"100%\" height=\"100%\" src=\""+slideItem.getVideo()+"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
                webView.loadData(iframe,"text/html","utf-8");
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebChromeClient(new WebChromeClient());
            } else {
                // If you want to display image from internet you can put code here using glide or picaso
                imageView.setImageResource(slideItem.getImage());
            }

        }
    }
}
