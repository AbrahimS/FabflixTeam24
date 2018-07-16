package brianandsteve.fabflixmobile;

import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void connectToTomcat(View view, String email, String password) {

        // Post request form data
        final Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        // no user is logged in, so we must connect to the server

        // Use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        // 10.0.2.2 is the host machine when running the android emulator


        final StringRequest loginRequest = new StringRequest(Request.Method.POST, "https://ec2-18-216-78-253.us-east-2.compute.amazonaws.com:8443/Fabflix/AndroidLoginServlet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);
                        if(response.equals("success"))
                        {
                            ((TextView) findViewById(R.id.errorTextView)).setText("Success");
                            final TextView errorTextViewField = findViewById(R.id.errorTextView);
                            String errorText = errorTextViewField.getText().toString();
                            Log.d("debug", errorText);
                            openSearch();
                        }
                        else
                        {
                            ((TextView) findViewById(R.id.errorTextView)).setText(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }  // HTTP POST Form Data
        };
        queue.add(loginRequest);
    }

    public void openSearch()
    {
        Intent openSearch = new Intent(this, SearchActivity.class);
        startActivity(openSearch);
    }


    public void launchSearch(View view)
    {
        final EditText emailField = findViewById(R.id.emailText);
        String email = emailField.getText().toString();

        final EditText passwordField = findViewById(R.id.passwordText);
        String password = passwordField.getText().toString();
        if(email.length() == 0)
        {
            emailField.setError("Email is required!");
        }
        else if(password.length() == 0)
        {
            passwordField.setError("Password is required!");
        }
        else
        {
            connectToTomcat(view, email, password);


        }
    }
}
