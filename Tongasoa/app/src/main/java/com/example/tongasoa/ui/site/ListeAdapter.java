package com.example.tongasoa.ui.site;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongasoa.R;
import com.example.tongasoa.modele.Site;

import java.util.ArrayList;

public class ListeAdapter extends RecyclerView.Adapter<ListeAdapter.viewHolder>{
    private ArrayList<Site> sites;
    private Context ctx;

    public ListeAdapter(ArrayList<Site> sites, Context ctx) {
        this.sites = sites;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.fragment_site_card, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Site site = this.sites.get(position);
        holder.nameSite.setText(site.getName());
        holder.imageSite.setImageResource(R.drawable.home_image2);
    }

    @Override
    public int getItemCount() {
        return this.sites.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView imageSite;
        TextView nameSite;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageSite = itemView.findViewById(R.id.imageSite);
            nameSite = itemView.findViewById(R.id.nameSite);
        }
    }
}
