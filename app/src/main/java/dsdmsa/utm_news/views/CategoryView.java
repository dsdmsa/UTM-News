package dsdmsa.utm_news.views;


import java.util.List;

import dsdmsa.utm_news.models.Category;

public interface CategoryView extends LoadingView {
    void showCategories(List<Category> categories);
}
