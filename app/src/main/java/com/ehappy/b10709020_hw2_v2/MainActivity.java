package com.ehappy.b10709020_hw2_v2;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ehappy.b10709020_hw2_v2.data.DBContract;
import com.ehappy.b10709020_hw2_v2.data.DBHelper;
import com.ehappy.b10709020_hw2_v2.data.TestUtil;

public class MainActivity extends AppCompatActivity {

    private ListAdapter mAdapter;

    private SQLiteDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //RecycleView
        RecyclerView list = (RecyclerView)this.findViewById(R.id.itemview);

        list.setLayoutManager(new LinearLayoutManager(this));

        DBHelper dbHelper = new DBHelper(this);

        mDB = dbHelper.getWritableDatabase();

        //TestUtil.insertFakeData(mDB);

        Cursor cursor = getAllGuests();

        mAdapter = new ListAdapter(this, cursor);

        // Link the adapter to the RecyclerView
        list.setAdapter(mAdapter);
/*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
      */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(MainActivity.this).setTitle("確定刪除?")
                        .setPositiveButton("確定",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                long id = (long) viewHolder.itemView.getTag();
                                //System.out.println(id);
                                DelGuest(id);
                                mAdapter.swapCursor(getAllGuests());
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { mAdapter.swapCursor(getAllGuests());}
                }).show();
            }
        }).attachToRecyclerView(list);
    }

    private boolean DelGuest(long id){
        return mDB.delete(DBContract.DBEntry.T_NAME, DBContract.DBEntry._ID+"="+id,null)>0;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.add:
                Intent intent = new Intent(MainActivity.this,add.class);
                startActivityForResult(intent,1);
                return true;

            case R.id.action_setting:
                Intent setting = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(setting);
                return true;
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    private Cursor getAllGuests() {
        return mDB.query(
                DBContract.DBEntry.T_NAME,
                null,
                null,
                null,
                null,
                null,
                DBContract.DBEntry.COLUMN_TIMESTAMP
        );
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == 1){
            if (resultCode == RESULT_OK) {
                String name = intent.getStringExtra("Name");
                int people = intent.getIntExtra("People",0);
                addNewGuest(name,people);

                mAdapter.swapCursor(getAllGuests());

                System.out.println(name + " " + people);
            }
        }
    }

    private long addNewGuest(String name, int people) {
        // COMPLETED (5) Inside, create a ContentValues instance to pass the values onto the insert query
        ContentValues cv = new ContentValues();
        // COMPLETED (6) call put to insert the name value with the key COLUMN_GUEST_NAME
        cv.put(DBContract.DBEntry.COLUMN_NAME, name);
        // COMPLETED (7) call put to insert the party size value with the key COLUMN_PARTY_SIZE
        cv.put(DBContract.DBEntry.COLUMN_PEOPLE, people);
        // COMPLETED (8) call insert to run an insert query on TABLE_NAME with the ContentValues created
        return mDB.insert(DBContract.DBEntry.T_NAME, null, cv);
    }
}
