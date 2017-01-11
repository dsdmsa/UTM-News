package dsdmsa.utm_news.activityes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dsdmsa.utm_news.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
//        ButterKnife.bind(this);
        setTheme(R.style.NoActionBar);
    }

}
