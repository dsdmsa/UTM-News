package dsdmsa.utmnews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.BuildConfig;
import dsdmsa.utmnews.R;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class AboutFragment extends BaseFragment {

    @BindView(R.id.app_name)
    TextView appNameTextView;

    @BindView(R.id.app_version)
    TextView appVersionTextView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_about;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appNameTextView.setText("Name : " + getString(R.string.app_name));
        appVersionTextView.setText("Version : " + BuildConfig.VERSION_NAME);

    }

    @Override
    public String getTitle() {
        return App.getAppComponent().getContext().getString(R.string.about_title);
    }
}
