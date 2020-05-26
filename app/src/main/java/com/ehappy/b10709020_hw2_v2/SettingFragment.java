package com.ehappy.b10709020_hw2_v2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference!= null) {
            String value = sharedPreferences.getString(preference.getKey(), "");
            setPreferenceSummary(preference, value);
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref);
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        Preference p = preferenceScreen.getPreference(0);
        String value = sharedPreferences.getString(p.getKey(), "");
        setPreferenceSummary(p, value);
    }

    private void setPreferenceSummary(Preference p, String value) {
        ListPreference listPreference = (ListPreference) p;
        int prefIndex = listPreference.findIndexOfValue(value);
        if (prefIndex >= 0) {
            listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            System.out.println(listPreference.getEntries()[prefIndex]);
        }
    }
}
