package dsdmsa.utm_news.activityes.news;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dsdmsa.utm_news.R;
import dsdmsa.utm_news.activityes.BaseActivity;

public class NewsActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webView;

    @Override
    protected int getLayout() {
        return R.layout.activity_news;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://utm.md/blog/omul-anului-2016/");
    }
}
