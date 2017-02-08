package dsdmsa.utmnews.views;


import java.util.List;

import dsdmsa.utmnews.models.Category;

public interface CategoryView extends LoadingView {
    void showCategories(List<Category> categories);
}
