package brianandsteve.fabflixmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void sqlSearch(View view, String movieTitle)
    {
        Log.d("Action: ", "sqlSearch begun");
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        final StringRequest searchRequest = new StringRequest(Request.Method.GET, "https://ec2-18-216-78-253.us-east-2.compute.amazonaws.com:8443/Fabflix/AndroidSearch?movieTitle="+movieTitle ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response2", response);
                        jsonPassing(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        );

        queue.add(searchRequest);
    }

    public void jsonPassing(String jsonData)
    {
        //parsing jsonData
        try
        {
            jsonData = jsonData.replace("Served at: /Fabflix", "");

            Log.d("DEBUG", jsonData);
            JSONArray jsonArray = new JSONArray(jsonData);
            ArrayList<Movie> movieList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++)
            {
                Log.d("JSON INFO: ", jsonArray.getString(i));
                JSONObject obj = (JSONObject)jsonArray.getJSONObject(i);
                movieList.add(new Movie(obj.getString("id"), obj.getString("title"),obj.getString("year"),obj.getString("director"),obj.getString("genres"),obj.getString("stars"), obj.getString("rating")));
            }
            Intent openMovieList = new Intent(this, MovieListActivity.class);
            openMovieList.putExtra("movieList", movieList);
            startActivity(openMovieList);

        }
        catch(JSONException e)
        {
            Log.d("Error: ", e.toString());
        }

    }
    public void launchSearchList(View view)
    {
        Log.d("Action: ", "launchSearch begun");
        final EditText movieField = findViewById(R.id.movieEditText);
        String movieTitle = movieField.getText().toString();
        if(movieTitle.length() == 0)
        {
            movieField.setError("Movie title is required!");
        }
        else
        {
            Log.d("Action: ", "calling SQL Search");
            sqlSearch(view, movieTitle);

        }
    }
}
