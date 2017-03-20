package dsdmsa.utmnews.views;


import java.util.List;

import dsdmsa.utmnews.models.Post;

public interface NewsView extends LoadingView{
    void showNews(List<Post> newses);
    void addNewses(List<Post> newses);
}
