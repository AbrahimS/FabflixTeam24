package brianandsteve.fabflixmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
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

public class MovieListActivity extends AppCompatActivity {

    private int numOfMovies;
    private int pageCount;
    private int max_per_page =10;
    private int current_page = 0;
    private ArrayList<Movie> movList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        movList = (ArrayList<Movie>) getIntent().getSerializableExtra("movieList");
        numOfMovies = movList.size();
        int value = numOfMovies % max_per_page;
        pageCount = numOfMovies/max_per_page + value;
        Button buttonPrev = (Button)findViewById(R.id.prevButton);
        Button buttonNext = (Button)findViewById(R.id.nextButton);

        buttonPrev.setEnabled(false);
        if(movList.size() <= max_per_page)
        {
            buttonNext.setEnabled(false);
        }

        loadMovies(current_page);
    }

    public void next(View view)
    {
        Button buttonPrev = (Button)findViewById(R.id.prevButton);
        Button buttonNext = (Button)findViewById(R.id.nextButton);

        if(current_page + 1 <= pageCount)
        {
            current_page++;
            if(!buttonPrev.isEnabled())
            {
                buttonPrev.setEnabled(true);
            }
            if(current_page + 1 > pageCount)
            {
                buttonNext.setEnabled(false);
            }
            else
            {
                buttonNext.setEnabled(true);
            }
            loadMovies(current_page);
        }
    }

    public void prev(View view)
    {
        Button buttonPrev = (Button)findViewById(R.id.prevButton);
        Button buttonNext = (Button)findViewById(R.id.nextButton);
        if(current_page - 1 >= 0)
        {
            current_page--;
            if(!buttonNext.isEnabled())
            {
                buttonNext.setEnabled(true);
            }
            if(current_page - 1 < 0)
            {
                buttonPrev.setEnabled(false);
            }
            else
            {
                buttonPrev.setEnabled(true);
            }
            loadMovies(current_page);
        }
    }

    public void loadMovies(int num)
    {
        final ArrayList<Movie> movies = new ArrayList<>();
        Button buttonNext = (Button)findViewById(R.id.nextButton);
        int max = num * max_per_page + max_per_page;

        if(max >= movList.size())
        {
            max = movList.size();
            buttonNext.setEnabled(false);
        }

        for(int i = (num * max_per_page); i < max; i++)
        {
            movies.add(movList.get(i));
        }
        MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);

        ListView listView = (ListView)findViewById(R.id.movieListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movies.get(position);
                Log.d("Clicked on position: ", position + " " + movie.getTitle());
                loadSingleMovie(movie);
            }
        });
    }

    public void loadSingleMovie(Movie movie)
    {
        Intent openSingleMovie = new Intent(this, SingleMovieActivity.class);
        openSingleMovie.putExtra("movie", movie);
        startActivity(openSingleMovie);
    }
}
