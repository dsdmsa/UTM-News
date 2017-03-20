package dsdmsa.utmnews.activityes.news;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.activityes.BaseActivity;

public class NewsActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webView;

    private String post;

    @Override
    protected int getLayout() {
        return R.layout.activity_news;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        post =  getIntent().getStringExtra("POST");

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadDataWithBaseURL("", post, "text/html", "UTF-8", "");


    }
}
