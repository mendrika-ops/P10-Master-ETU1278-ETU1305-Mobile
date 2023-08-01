package com.example.tongasoa.ui.site;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tongasoa.R;
import com.example.tongasoa.controle.SiteControleur;
import com.example.tongasoa.modele.Site;

import java.util.ArrayList;
import java.util.List;

public class Sites extends Fragment {

    private SiteControleur siteControleur;
    private RecyclerView recycler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sites, container, false);
       /* if(this.siteControleur == null) {
            this.siteControleur = SiteControleur.getInstance(this.getContext());
        }
        recycler = view.findViewById(R.id.recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
        ArrayList<View> listeView = new ArrayList<>();
        List sites = this.siteControleur.getSites();
        for(int i = 1; i < sites.size();i++) {
         //   listeView.add((View)SiteCard.newInstance((Site) sites.get(i)));
        }
        recycler.addFocusables(listeView, 1);*/
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        recycler = view.findViewById(R.id.recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(false);

        Site site = new Site("1", "mety", "mety", "mety", "mety", "mety", null);
        Site site2 = new Site("2", "mety2", "mety2", "mety2", "mety2", "mety2", null);
        Site site3 = new Site("3", "mety3", "mety3", "mety3", "mety3", "mety3", null);
        Site site4 = new Site("3", "mety3", "mety3", "mety3", "mety3", "mety3", null);
        Site site5 = new Site("3", "mety3", "mety3", "mety3", "mety3", "mety3", null);
        Site site6 = new Site("3", "mety3", "mety3", "mety3", "mety3", "mety3", null);
        Site site7 = new Site("3", "mety3", "mety3", "mety3", "mety3", "mety3", null);
        Site site8 = new Site("3", "mety3", "mety3", "mety3", "mety3", "mety3", null);
        Site site9 = new Site("3", "mety3", "mety3", "mety3", "mety3", "mety3", null);
        ArrayList<Site> sites = new ArrayList<Site>();
        sites.add(site);
        sites.add(site2);
        sites.add(site3);
        sites.add(site4);
        sites.add(site5);
        sites.add(site6);
        sites.add(site7);
        sites.add(site8);
        sites.add(site9);
        //ListeAdapter listSite = new ListeAdapter(this.siteControleur.getSites(), this.getContext());
        ListeAdapter listSite = new ListeAdapter(sites, this.getContext());
        recycler.setAdapter(listSite);
        listSite.notifyDataSetChanged();
    }

    private void dataInitialize() {
        if(this.siteControleur == null) {
            this.siteControleur = SiteControleur.getInstance(this.getContext());
        }
    }
}