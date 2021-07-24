package com.example.cryptotracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

//Api used is CoinMarketCap



public class MainActivity extends AppCompatActivity {

    ArrayList<CoinModel> items;
    CoinAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.idPBLoading);
        searchView = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recyclerView);
        items = new ArrayList<>();
        // initializing our adapter class.
        adapter = new CoinAdapter(this,items);
        // setting layout manager to recycler view.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // setting adapter to recycler view.
        recyclerView.setAdapter(adapter);

        getAPIData();

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void getAPIData() {

        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue queue = Volley.newRequestQueue(this);
        // making a json object request to fetch data from API.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // inside on response method extracting data from response and passing it to array list

                // on below line we are making our progress bar visibility to gone.
                progressBar.setVisibility(View.GONE);
                try{
                    JSONArray dataArray = response.getJSONArray("data");
                    for(int i=0;i<dataArray.length();i++){
                        JSONObject dataObj =dataArray.getJSONObject(i);
                        String symbol = dataObj.getString("symbol");
                        String name = dataObj.getString("name");
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");
                        items.add(new CoinModel(name,symbol,price));
                    }
                    adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"Something went wrong! Please Try Again Later",Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Something went wrong! Please Try Again Later",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // calling a method to add our
                // json object request to our queue.
                HashMap<String,String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","f549497b-faba-4990-9eb5-a443f4ac92de");
                // at last returning headers
                return headers;
            }
        };
        // calling a method to add our
        // json object request to our queue.
        queue.add(jsonObjectRequest);
    }

    public void filter(String filter){
        // on below line we are creating a new array list
        // for storing our filtered data.
        ArrayList<CoinModel> filteredList = new ArrayList<>();
        // running a for loop to search the data from our array list.
        for(CoinModel item : items){
            // on below line we are getting the item which are
            // filtered and adding it to filtered list.
            if(item.getName().toLowerCase().contains(filter.toLowerCase())){
                filteredList.add(item);
            }
        }
        // on below line we are checking
        // weather the list is emoty or not.
        if(filteredList.isEmpty()){
            Toast.makeText(this,"No currency found",Toast.LENGTH_SHORT).show();
        }else{
            adapter.filterList(filteredList);
        }

    }
}