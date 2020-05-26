package com.ehappy.b10709020_hw2_v2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ehappy.b10709020_hw2_v2.data.DBContract;
import com.ehappy.b10709020_hw2_v2.data.DBHelper;
import com.ehappy.b10709020_hw2_v2.data.TestUtil;

public class add extends AppCompatActivity {

    String name;
    int people;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    Intent intent = new Intent();

    Button sure, cancle;
    EditText Name, People;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        Name = (EditText) findViewById(R.id.name);
        People = (EditText) findViewById(R.id.people);

    }

    public void cancle(View view) {
        finish();
    }

    public void add(View view) {
        if (Name.getText().length() == 0 || People.getText().length() == 0) {return;}
        try {
            //mNewPartyCountEditText inputType="number", so this should always work
            people = Integer.parseInt(People.getText().toString());
            name = Name.getText().toString();
        } catch (NumberFormatException ex) {
            // COMPLETED (12) Make sure you surround the Integer.parseInt with a try catch and log any exception
            Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.getMessage());
        }
        intent.putExtra("Name",name);
        intent.putExtra("People",people);
        setResult(RESULT_OK, intent);
        finish();
    }
}

