package dsdmsa.utmnews.activityes;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.ButterKnife;
import dsdmsa.utmnews.R;

public abstract class BaseActivity extends MvpAppCompatActivity {

    protected abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        setTheme(R.style.NoActionBar);
    }

}
