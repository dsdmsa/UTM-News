package dsdmsa.utm_news.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import dsdmsa.utm_news.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.preference_creen);
    }
}