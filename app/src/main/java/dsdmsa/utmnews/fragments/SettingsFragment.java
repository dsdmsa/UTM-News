package dsdmsa.utmnews.fragments;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class SettingsFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_about;
    }

    @Override
    public String getTitle() {
        return App.getAppComponent().getContext().getString(R.string.settings_title);
    }


}
