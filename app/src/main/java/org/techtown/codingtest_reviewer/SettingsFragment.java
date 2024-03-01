package org.techtown.codingtest_reviewer;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat {

    ListPreference listPreference;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        if(rootKey == null)
            listPreference = findPreference("date_interval");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        listPreference.setSummary(getDateInterval()+"일");

        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                listPreference.setSummary(getDateInterval()+"일");
                return true;
            }
        });

    }

    private String getDateInterval(){
        String dateInterval = sharedPreferences.getString("date_interval", "0");

        return dateInterval;
    }
}