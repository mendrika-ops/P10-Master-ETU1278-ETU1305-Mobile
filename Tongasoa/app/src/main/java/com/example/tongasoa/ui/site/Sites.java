package com.example.tongasoa.ui.site;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tongasoa.R;
import com.example.tongasoa.controle.SiteControleur;
import com.example.tongasoa.modele.Category;
import com.example.tongasoa.modele.Commentaire;
import com.example.tongasoa.modele.Media;
import com.example.tongasoa.modele.Region;
import com.example.tongasoa.modele.Site;
import com.example.tongasoa.utils.Constante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

public class Sites extends Fragment {

    private SiteControleur siteControleur;
    private RecyclerView recycler;
    private FragmentManager fragmentManager;

    private RequestQueue queue;
    ProgressBar loadingSpinner;

    ArrayList<Site> sites;
    public Sites(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sites, container, false);
        loadingSpinner = view.findViewById(R.id.progressBar);
        loadingSpinner.setVisibility(View.VISIBLE);
        recyclerViewInitialize(view, null);
        Context ctx = view.getContext();
        final String[] lasText = {""};

        // Find the SearchView in your layout
        SearchView searchView = view.findViewById(R.id.searchView);
        // Set the OnQueryTextListener for the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // This method will be called when the user submits the search query
                // Here, you can perform the search operation or other tasks based on the query
                Toast.makeText(ctx, "Rechercher : '" + query+"'", Toast.LENGTH_SHORT).show();
                lasText[0] = query;
                loadingSpinner.setVisibility(View.VISIBLE);
                recyclerViewInitialize(view, query);
                return true; // Return true to indicate that the query has been handled
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // This method will be called whenever the user changes the text in the search field
                // You can perform search operations as the user types or update the search results
                if(lasText[0] != "" && newText.isEmpty()){
                    recyclerViewInitialize(view, null);
                }
                return false; // Return false to let the SearchView handle the text change
            }

        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void recyclerViewInitialize(View view, String search) {
        if(this.siteControleur == null) {
            this.siteControleur = SiteControleur.getInstance(this.getContext());
        }
        queue = this.siteControleur.getRequestQueue();
        getListeSite(this.getContext(), view, search);
    }

    /**
     * Get Liste Site depuis le back
     * @param ctx
     * @return ArrayList<Site>
     */
    private void getListeSite(Context ctx, View view, String search) {
        try {
            String api = Constante.BASE_URL+"site";
            // Créer l'objet Uri.Builder pour construire l'URL avec les paramètres
            Uri.Builder builder = Uri.parse(api).buildUpon();
            sites = new ArrayList<Site>();

            if (search != null ) {
                // Ajouter les paramètres à l'URL encodée
                builder.appendQueryParameter("search", search);
            }

            // Construire l'URL finale avec les paramètres
            String urlWithParams = builder.build().toString();

            recycler = view.findViewById(R.id.recyclerview);
            GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
            recycler.setLayoutManager(layoutManager);
            recycler.setHasFixedSize(false);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlWithParams, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonresponse) {
                            try {
                                JSONArray array = jsonresponse.getJSONArray("rows");
                                for(int i =0 ; i< array.length(); i++){
                                    JSONObject simpleObject = array.getJSONObject(i);
                                    Site site = new Site(
                                            simpleObject.getString("id"),
                                            simpleObject.getString("idRegion"),
                                            simpleObject.getString("idCategory"),
                                            simpleObject.getString("name"),
                                            simpleObject.getString("description"),
                                            simpleObject.getString("link"),
                                            (float) simpleObject.getDouble("rating"),
                                            Date.valueOf(simpleObject.getString("createdDate"))
                                    );
                                    JSONObject jsonRegion = simpleObject.getJSONObject("Region");
                                    JSONObject jsonCategory = simpleObject.getJSONObject("Category");
                                    JSONArray jsonMedias = simpleObject.getJSONArray("Media");
                                    JSONArray jsonCommentaire = simpleObject.getJSONArray("Commentaire");
                                    site.setMedias(new ArrayList<Media>());
                                    for(int ii =0 ; ii< jsonMedias.length(); ii++){
                                        JSONObject jsonMedia = jsonMedias.getJSONObject(ii);
                                        Media media = new Media(jsonMedia.getInt("id"), jsonMedia.getString("link"));
                                        site.getMedias().add(media);
                                    }

                                    site.setCommentaires(new ArrayList<Commentaire>());
                                    for(int iii =0 ; iii< jsonCommentaire.length(); iii++){
                                        JSONObject jsonCom = jsonCommentaire.getJSONObject(iii);
                                        Commentaire coms = new Commentaire(String.valueOf(jsonCom.getInt("id")), String.valueOf(jsonCom.getInt("idUser")), String.valueOf(jsonCom.getInt("idSite")), jsonCom.getString("commentaire"), jsonCom.getString("note"), jsonCom.getString("createDate"));
                                        site.getCommentaires().add(coms);
                                    }
                                    Region region = new Region(jsonRegion.getString("id"), jsonRegion.getString("idProvince"), jsonRegion.getString("name"));
                                    Category category = new Category(jsonCategory.getString("id"), jsonCategory.getString("name"));
                                    site.setRegion(region);
                                    site.setCategory(category);

                                    sites.add(site);
                                }
                                //ListeAdapter listSite = new ListeAdapter(this.siteControleur.getSites(), this.getContext());
                                ListeAdapter listSite = new ListeAdapter(sites, ctx, fragmentManager);
                                recycler.setAdapter(listSite);
                                listSite.notifyDataSetChanged();
                                loadingSpinner.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("api", "onErrorResponse: "+ error.getLocalizedMessage());
                }
            });

            queue.add(jsonObjectRequest);
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }
}