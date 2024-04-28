package com.example.android_harjoitukset;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android_harjoitukset.utils.Item;
import com.example.android_harjoitukset.utils.RecycleAdapter;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private ArrayList<Item> itemList;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private RecycleAdapter adapter;

    public LinearProgressIndicator progressBar;
    private Toolbar toolbar;

    public String url = "https://avoindata.prh.fi/bis/v1?totalResults=false&maxResults=100&resultsFrom=0&name=xxx&companyRegistrationFrom=2000-01-01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.searchBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        progressBar = findViewById(R.id.progBar);
        recyclerView = findViewById(R.id.YTJsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String searchTxt = extras.getString("searchTxt");
        if (searchTxt != null) {
            url = url.replaceFirst("xxx", searchTxt);
            Log.i(TAG, "url: " + url);
        }

        retrieveJSON();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                Log.i(TAG, "SEARCHING: " + query);
                return false;

            }
        });
        return true;
    }

    private void retrieveJSON() {

        progressBar.show();



        requestQueue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        itemList = new ArrayList<Item>();
                        try {
                            JSONArray responseItems = response.getJSONArray("results");



                            for (int i = 0; i < responseItems.length(); i++) {

                                Item item = new Item();
                                JSONObject dataobj = responseItems.getJSONObject(i);
                                item.setName(dataobj.getString("name"));
                                item.setId(dataobj.getString("businessId"));
                                item.setCompanyForm(dataobj.getString("companyForm"));
                                item.setRegistrationDate(dataobj.getString("registrationDate"));
                                itemList.add(item);



                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "JsonobjectRequest error: " + e.getMessage());
                        }
                        //Log.i(TAG, "Data found: " + itemList.size());
                        setUpView();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        if (error != null) {
                            Log.e(TAG, "prepareData: " + error.getMessage());
                        }
                        progressBar.hide();
                        progressBar.setVisibilityAfterHide(View.INVISIBLE);
                    }
                }
                );
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        requestQueue.add(jsonObjectRequest);

    }

    private void setUpView() {
        progressBar.setVisibility(View.GONE);

        adapter = new RecycleAdapter(itemList);

        recyclerView.setAdapter(adapter);
    }
}