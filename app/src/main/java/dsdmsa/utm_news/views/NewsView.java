package dsdmsa.utm_news.views;


import java.util.List;

import dsdmsa.utm_news.models.News;

public interface NewsView extends LoadingView{
    void showNews(List<News> newses);
    void addNewses(List<News> newses);
}
