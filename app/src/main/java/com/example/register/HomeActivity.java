package com.example.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner sort_spinner;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Movie> movieList;
    private List<Movie> dummy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sort_spinner = findViewById(R.id.sort_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerlist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sort_spinner.setAdapter(adapter);

        sort_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String pokemon = adapterView.getItemAtPosition(position).toString();
                dummy = movieList.stream()
                        .filter(data -> data.getType().equals(pokemon))
                        .collect(Collectors.toList());

                MovieAdapter adapter = new MovieAdapter(HomeActivity.this, dummy);
                recyclerView.setAdapter(adapter);

                Toast.makeText(HomeActivity.this, pokemon, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        movieList = new ArrayList<>();
        fetchMovies();
    }

    private void fetchMovies() {
//        String url = "https://dummyapi.online/api/pokemon";
//        String url = "http://192.168.45.43/mad_api/myapi.php";
//        String url = "http://localhost/mad_api/myapi.php";
        String url = "http://192.168.154.222/MAD_Project/backend/api/getAllUsers.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("name", "This is working");

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String title = jsonObject.getString("username");
                                String overview = jsonObject.getString("bio");
                                String poster = jsonObject.getString("profile_photo");
                                Double rating = jsonObject.getDouble("posts");

                                Movie movie = new Movie(title, poster, overview, rating);
                                movieList.add(movie);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        MovieAdapter adapter = new MovieAdapter(HomeActivity.this, movieList);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}