package com.example.tongasoa.ui.site;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tongasoa.R;
import com.example.tongasoa.modele.Media;
import com.example.tongasoa.modele.Site;
import com.example.tongasoa.vue.SitePage;

import java.util.ArrayList;

public class ListeAdapter extends RecyclerView.Adapter<ListeAdapter.viewHolder>{
    private ArrayList<Site> sites;
    private Context ctx;

    private FragmentManager fragmentManager;

    public ListeAdapter(ArrayList<Site> sites, Context ctx, FragmentManager fragmentManager) {
        this.sites = sites;
        this.ctx = ctx;
        this.fragmentManager = fragmentManager;
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
        Media media = site.getMedias().get(0);
        Glide.with(ctx).load(media.getLink()).into(holder.imageSite);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une instance du Fragment "Sites"
                Fragment sitesFragment = new SitePage(site);

                // Commencer une transaction de fragment
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Remplacer le contenu du conteneur principal par le fragment "Sites"
                fragmentTransaction.replace(R.id.fragmentHome, sitesFragment);
                // Ajouter la transaction au back stack (pour permettre le retour en arrière)
                fragmentTransaction.addToBackStack(null);

                // Confirmer la transaction
                fragmentTransaction.commit();
            }
        });
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
