package dsdmsa.utmnews.views;


import java.util.List;

import dsdmsa.utmnews.models.News;

public interface NewsView extends LoadingView{
    void showNews(List<News> newses);
    void addNewses(List<News> newses);
}
