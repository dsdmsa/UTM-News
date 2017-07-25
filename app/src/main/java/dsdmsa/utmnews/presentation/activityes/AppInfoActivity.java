package dsdmsa.utmnews.presentation.activityes;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dsdmsa.utmnews.BuildConfig;
import dsdmsa.utmnews.R;


public class AppInfoActivity extends BaseActivity {

    @BindView(R.id.app_version)
    TextView appVersion;

    @Override
    protected int getLayout() {
        return R.layout.activity_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        appVersion.setText(getString(R.string.about_version) + " " + BuildConfig.VERSION_NAME);
    }
}
