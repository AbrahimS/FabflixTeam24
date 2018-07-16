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

public class SingleMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);
        Intent intent = getIntent();
        Movie movie = (Movie)intent.getSerializableExtra("movie");

        TextView movieTitleView = (TextView)this.findViewById(R.id.movieTitleView);
        TextView yearTextView = (TextView)this.findViewById(R.id.movieYearView);
        TextView directorTextView = (TextView)this.findViewById(R.id.movieDirectorView);
        TextView genresTextView = (TextView)this.findViewById(R.id.movieGenresView);
        TextView starsTextView = (TextView)this.findViewById(R.id.movieStarsView);
        TextView ratingTextView = (TextView)this.findViewById(R.id.movieRatingView);

        movieTitleView.setText("Title:\n" + movie.getTitle());
        yearTextView.setText("Year:\n" + movie.getYear());
        directorTextView.setText("Director:\n" + movie.getDirector());
        genresTextView.setText("Genres:\n" + movie.getGenres());
        starsTextView.setText("Stars:\n" + movie.getStars());
        ratingTextView.setText("Rating:\n"+movie.getRating());

    }
}
