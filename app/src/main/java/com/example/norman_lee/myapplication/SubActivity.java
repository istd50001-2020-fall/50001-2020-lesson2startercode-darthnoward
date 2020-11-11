package com.example.norman_lee.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    Button buttonBackToCalculator;
    EditText editTextSubValueOfA;
    EditText editTextSubValueOfB;
    public final static String INTENT_EXCH_RATE = "Exchange Rate";
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.subsharedprefs";
    public final static String A_KEY = "A_KEY";
    public final static String B_KEY = "B_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);


        //TODO 4.9 Implement saving to shared preferences for the contents of the EditText widget

        editTextSubValueOfA = findViewById(R.id.editTextSubValueA);
        editTextSubValueOfB = findViewById(R.id.editTextSubValueB);
        buttonBackToCalculator = findViewById(R.id.buttonBackToCalculator);
        buttonBackToCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Utils.checkInvalidInputs(editTextSubValueOfA);
                    Utils.checkInvalidInputs(editTextSubValueOfB);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(A_KEY, editTextSubValueOfA.getText().toString());
                    intent.putExtra(B_KEY, editTextSubValueOfB.getText().toString());
                    startActivity(intent);
                } catch (NumberFormatException e){
                    Toast.makeText(SubActivity.this, "empty string", Toast.LENGTH_LONG).show();
                } catch (IllegalArgumentException e){
                    Toast.makeText(SubActivity.this, "cannot be 0", Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(SubActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        //TODO 3.8 Obtain the values stored in the editTextWidgets
        //TODO 3.9 Check that these values are valid --> See the Utils class
        //TODO 3.10 Set up an explicit intent and pass the exchange rate back to MainActivity
        //TODO 3.11 Decide how you are going to handle a divide-by-zero situation or when negative numbers are entered
        //TODO 3.12 Decide how you are going to handle a situation when the editText widgets are empty

    }

    static class Utils {
        public static void checkInvalidInputs(EditText input){
            if ( Double.valueOf(input.getText().toString()) <= 0 ){
                throw new IllegalArgumentException();
            }
        }
    }

    //TODO 4.10 Don't forget to override onPause()

}
