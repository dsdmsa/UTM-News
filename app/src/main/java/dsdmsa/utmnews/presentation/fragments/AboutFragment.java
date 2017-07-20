package dsdmsa.utmnews.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.BuildConfig;
import dsdmsa.utmnews.R;


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

        appNameTextView.setText(getString(R.string.about_name) + getString(R.string.app_name));
        appVersionTextView.setText(getString(R.string.about_version) + BuildConfig.VERSION_NAME);

    }

    @Override
    public String getTitle() {
        return App.getAppComponent().getApp().getString(R.string.about_title);
    }

    @Override
    public void retry() {

    }
}