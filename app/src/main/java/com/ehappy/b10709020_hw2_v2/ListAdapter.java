package com.ehappy.b10709020_hw2_v2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;
import com.ehappy.b10709020_hw2_v2.data.DBContract.*;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements SharedPreferences.OnSharedPreferenceChangeListener {
    private Context mContext;
    private Cursor mCursor;
    private Resources r ;
    public SharedPreferences sharedPreferences;
    private TextView nameTextView;
    private TextView partySizeTextView;

    public ListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        r = context.getResources();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;

        String name = mCursor.getString(mCursor.getColumnIndex(DBEntry.COLUMN_NAME));

        int people = mCursor.getInt(mCursor.getColumnIndex(DBEntry.COLUMN_PEOPLE));

        long id = mCursor.getLong(mCursor.getColumnIndex(DBEntry._ID));

        nameTextView = holder.Name;
        partySizeTextView = holder.People;

        nameTextView.setText(name);
        GradientDrawable background = (GradientDrawable) partySizeTextView.getBackground();
        //background.setColor(Color.parseColor(r.getString(R.string.pref_dark_value)));
        background.setColor(Color.parseColor(sharedPreferences.getString(r.getString(R.string.pref_label_key),r.getString(R.string.pref_dark_value))));
        // Display the party count
        partySizeTextView.setText(String.valueOf(people));

        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        GradientDrawable background = (GradientDrawable) partySizeTextView.getBackground();
        background.setColor(Color.parseColor(sharedPreferences.getString(r.getString(R.string.pref_label_key),r.getString(R.string.pref_dark_value))));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView Name;
        TextView People;

        public ViewHolder(View v) {
            super(v);
            Name = (TextView) v.findViewById(R.id.name);
            People = (TextView) v.findViewById(R.id.people);
        }
    }

    public void swapCursor(Cursor newCursor) {
        // COMPLETED (16) Inside, check if the current cursor is not null, and close it if so
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        // COMPLETED (17) Update the local mCursor to be equal to  newCursor
        mCursor = newCursor;
        // COMPLETED (18) Check if the newCursor is not null, and call this.notifyDataSetChanged() if so
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }
}
