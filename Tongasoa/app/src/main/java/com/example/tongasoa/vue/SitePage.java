package com.example.tongasoa.vue;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tongasoa.R;
import com.example.tongasoa.modele.Commentaire;
import com.example.tongasoa.modele.Favoris;
import com.example.tongasoa.modele.Media;
import com.example.tongasoa.modele.Site;
import com.example.tongasoa.modele.User;
import com.example.tongasoa.ui.commentaire.ListeCommentaire;
import com.example.tongasoa.utils.Constante;
import com.example.tongasoa.utils.VerticalSpaceItemDecoration;
import com.example.tongasoa.utils.slide.SlideAdapter;
import com.example.tongasoa.utils.slide.SlideItem;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SitePage extends Fragment {

    private ViewPager2 viewPager2;
    private WebView webView;
    private Handler slideHandler = new Handler();

    private TextView nameSite;
    private TextView description;

    private TextView textcomment;

    private TextView reaction;
    private RatingBar ratingBar;

    private long timeWaiting = 3000;

    private Site site;

    ArrayList<Commentaire> commentaire;
    private RecyclerView recycler;

    private FragmentManager fragmentManager;

    private Runnable sliderHandler = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
            slideHandler.removeCallbacks(sliderHandler);
            slideHandler.postDelayed(sliderHandler, timeWaiting);
        }
    };

    public SitePage(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
    public SitePage(Site site) {
        this.site = site;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_site_page, container, false);
        this.viewPager2 = view.findViewById(R.id.viewPager2);
        this.nameSite = view.findViewById(R.id.textViewNameSite);
        this.description = view.findViewById(R.id.textViewDescription);
        this.ratingBar = view.findViewById(R.id.ratingBarPage);
        this.reaction  = view.findViewById(R.id.reaction);
        this.ratingBar.setRating(site.getRating());
        reaction.setText("("+String.valueOf(site.getFavoris().size())+")");
        nameSite.setText(site.getName());
        description.setText(site.getDescription());
        List<SlideItem> slideItemList = new ArrayList<SlideItem>();
        ArrayList<Media> medias = site.getMedias();
        for (int i = 0; i < medias.size(); i++) {
            slideItemList.add(new SlideItem(0, medias.get(i).getLink()));
        }
        viewPager2.setAdapter(new SlideAdapter(slideItemList, viewPager2));

        recyclerViewInitialize(view);

        // iframe is compnent HMTL on the youtube share option
        webView = view.findViewById(R.id.webView);
        String iframe = "<iframe width=\"100%\" height=\"100%\" src=\"" + site.getLink() + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.loadData(iframe,"text/html","utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        slideHandler.removeCallbacks(sliderHandler);
        slideHandler.postDelayed(sliderHandler, timeWaiting);


        textcomment = (TextView) view.findViewById(R.id.textcomment);

        Button loginbtn = (Button) view.findViewById(R.id.btncomments);

        ProgressBar loadingSpinner = view.findViewById(R.id.loading_spinner);
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String valueUser = sharedPreferences.getString("user", null);
        boolean checked = false;
        boolean isConnected = (valueUser != null );

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commentaire comment = new Commentaire();
                loadingSpinner.setVisibility(View.VISIBLE);
                loginbtn.setEnabled(true);
                if(TextUtils.isEmpty(textcomment.getText().toString())){
                    textcomment.setError("Comments required");
                    loadingSpinner.setVisibility(View.GONE);
                    loginbtn.setEnabled(true);
                    return;
                }

                comment.setIdSite(site.getId());
                comment.setCommentaire(textcomment.getText().toString());
                comment.setIdUser("1");
                comment.setNote("Guest :)");
                if(isConnected){
                    User user = gson.fromJson(valueUser, User.class);
                    comment.setIdUser(user.getId());
                    comment.setNote(user.getNomPrenom());
                }

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(currentDate);
                comment.setCreateDate(formattedDate);
                createCommentaire(comment);
                textcomment.setText("");
                loadingSpinner.setVisibility(View.GONE);
                loginbtn.setEnabled(true);
            }
        });



        CheckBox myCheckbox = view.findViewById(R.id.heartCheckBox);
        if(isConnected) {
            User user = gson.fromJson(valueUser, User.class);
            for(int i=0 ;i< site.getFavoris().size();i++){
                Favoris fav = (Favoris) site.getFavoris().get(i);
                if(Integer.parseInt(fav.getIdUser()) == Integer.parseInt(user.getId()) && fav.getEtat() == Constante.ETAT_CREER){
                    checked = true;
                }
            }
            if(checked == true){
                myCheckbox.setChecked(true);
            }
            myCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // Checkbox is checked, do something
                String value = "Checkbox is checked";
                Log.println(Log.VERBOSE, "CHECKED ", "INDROO ");
                this.addFavoris(user);
            });
        }else{
            myCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // Checkbox is checked, do something
                String value = "Checkbox is not checked";
                myCheckbox.setChecked(false);
                Toast.makeText(SitePage.this.getContext(), "Permission denied, please connect in your account", Toast.LENGTH_LONG).show();
            });

        }


        return view;
    }

    private void recyclerViewInitialize(View view) {
        getCommentaire(this.getContext(), view);
    }
    private void getCommentaire(Context ctx, View view) {
        try {
            String api = Constante.BASE_URL+"site";
            // Créer l'objet Uri.Builder pour construire l'URL avec les paramètres
            Uri.Builder builder = Uri.parse(api).buildUpon();
            commentaire = new ArrayList<Commentaire>();

            // Construire l'URL finale avec les paramètres
            String urlWithParams = builder.build().toString();

            recycler = view.findViewById(R.id.recyclerview2);
            int verticalSpacing = R.dimen.vertical_spacing; // Créez une ressource dimension appropriée
            recycler.addItemDecoration(new VerticalSpaceItemDecoration(this.getContext(), verticalSpacing));

            LinearLayoutManager  layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(layoutManager);
            recycler.setHasFixedSize(false);

            //ListeAdapter listSite = new ListeAdapter(this.siteControleur.getSites(), this.getContext());
           /* commentaire.add(new Commentaire("Manahoana daholo nareo e", "Tatiana Rajoelina","05/08/2023 00:12"));
            commentaire.add(new Commentaire("Tena tsara ilay rova e", "Politika Madio" ,"05/08/2023 00:12"));*/

            ListeCommentaire listCommentaire = new ListeCommentaire(site.getCommentaires(), ctx, fragmentManager);

            recycler.setAdapter(listCommentaire);
            listCommentaire.notifyDataSetChanged();
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }

    private void createCommentaire(Commentaire com) {
        String url = Constante.BASE_URL+ "site/addCommentaire";
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("idSite", com.getIdSite());
            requestBody.put("idUser", com.getIdUser());
            requestBody.put("commentaire", com.getCommentaire());
            requestBody.put("note", com.getNote());
            requestBody.put("createDate", com.getCreateDate());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<User> allUser = new ArrayList<User>();
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        try{
                            Log.println(Log.VERBOSE, "CREATE COMMENTS", response.toString());
                            site.getCommentaires().add(com);
                            Fragment sitesFragment = new SitePage(site);
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.detach(sitesFragment);
                            fragmentTransaction.attach(sitesFragment);
                            fragmentTransaction.commit();
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.e("API create comments", "ERROR : "+ e.getMessage());

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: "+ error.getLocalizedMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void addFavoris(User user) {
        String url = Constante.BASE_URL+ "site/addFavoris";
        JSONObject requestBody = new JSONObject();
        try {
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(currentDate);
            requestBody.put("idSite", site.getId());
            requestBody.put("idUser", user.getId());
            requestBody.put("description", "added");
            requestBody.put("etat", Constante.ETAT_CREER);
            requestBody.put("createDate", formattedDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        try{
                            Log.println(Log.VERBOSE, "CREATE FAVORIS", response.toString());
                            site.getFavoris().add(new Favoris());
                            Fragment sitesFragment = new SitePage(site);
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.detach(sitesFragment);
                            fragmentTransaction.attach(sitesFragment);
                            fragmentTransaction.commit();
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.e("API create comments", "ERROR : "+ e.getMessage());

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: "+ error.getLocalizedMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }

}