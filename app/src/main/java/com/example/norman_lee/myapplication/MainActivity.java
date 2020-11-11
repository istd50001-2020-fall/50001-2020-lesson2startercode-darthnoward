package com.example.norman_lee.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonConvert;
    Button buttonSetExchangeRate;
    EditText editTextValue;
    TextView textViewResult;
    TextView textViewExchangeRate;
    double exchangeRate;
    ExchangeRate ex;
    public final String TAG = "Logcat";
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.mainsharedprefs";
    public static final String RATE_KEY = "Rate_Key";
    public static final String EDIT_KEY = "Edit_Key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String Rate_text = mPreferences.getString(RATE_KEY, "2.95000");
        exchangeRate = Double.valueOf(Rate_text);

        //TODO 4.6 Retrieve the value using the key, and set a default when there is none

        try {
            Intent intent = getIntent();
            ex = new ExchangeRate(intent.getStringExtra(SubActivity.A_KEY), intent.getStringExtra(SubActivity.B_KEY));
            exchangeRate = Double.valueOf(ex.getExchangeRate().toString());
        }
        catch (Exception e) {
            ex = new ExchangeRate(Rate_text);
        }
        //TODO 3.13a See ExchangeRate class --->

        editTextValue = findViewById(R.id.editTextValue);
        buttonConvert = findViewById(R.id.buttonConvert);
        textViewExchangeRate = findViewById(R.id.textViewExchangeRate);
        textViewResult = findViewById(R.id.textViewResult);

        textViewExchangeRate.setText(String.valueOf(exchangeRate));

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( editTextValue.getText().toString().isEmpty() ){
                    Toast.makeText(MainActivity.this, "The input is empty!",Toast.LENGTH_LONG).show();
                    Log.i(TAG, "The input is empty.");
                    return;
                }
                textViewResult.setText(ex.calculateAmount(editTextValue.getText().toString()).toString());
            }
        });

        buttonSetExchangeRate = findViewById(R.id.buttonSetExchangeRate);
        buttonSetExchangeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(RATE_KEY, String.valueOf(exchangeRate));
        preferencesEditor.putString(EDIT_KEY, editTextValue.getText().toString());
        preferencesEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //TODO 4.1 Go to res/menu/menu_main.xml and add a menu item Set Exchange Rate
    //TODO 4.2 In onOptionsItemSelected, add a new if-statement and code accordingly

    //TODO 5.1 Go to res/menu/menu_main.xml and add a menu item Open Map App
    //TODO 5.2 In onOptionsItemSelected, add a new if-statement
    //TODO 5.3 code the Uri object and set up the intent

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.setExRate) {
            MainActivity.this.buttonSetExchangeRate.performClick();
            return true;
        }

        if (id == R.id.goToMap){
            String location = getString(R.string.default_location);
            Uri.Builder builder = new Uri.Builder();
            builder.scheme( "geo" ).opaquePart( "0.0" ).appendQueryParameter( "q" ,location);
            Uri geoLocation = builder.build();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(geoLocation);
            if ( intent.resolveActivity(getPackageManager()) != null )
            { startActivity(intent); }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String Edit_text = mPreferences.getString(EDIT_KEY, "");
        editTextValue.setText(Edit_text);
        if (! editTextValue.getText().toString().isEmpty() && textViewResult.getText().toString()!="value") textViewResult.setText(ex.calculateAmount(editTextValue.getText().toString()).toString());
    }

    //TODO 4.3 override the methods in the Android Activity Lifecycle here
    //TODO 4.4 for each of them, write a suitable string to display in the Logcat

    //TODO 4.7 In onPause, get a reference to the SharedPreferences.Editor object
    //TODO 4.8 store the exchange rate using the putString method with a key

}
