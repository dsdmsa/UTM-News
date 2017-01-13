package dsdmsa.utm_news.activityes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import dsdmsa.utm_news.R;
import dsdmsa.utm_news.fragments.SettingsFragment;

public class PreferenceActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_preference;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Fragment preferenceFragment = new SettingsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.preference_container, preferenceFragment);
            ft.commit();
        }
    }
}
