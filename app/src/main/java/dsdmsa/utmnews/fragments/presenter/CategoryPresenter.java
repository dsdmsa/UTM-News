package dsdmsa.utmnews.fragments.presenter;


import javax.inject.Inject;

import dsdmsa.utmnews.network.controlers.UtmController;
import dsdmsa.utmnews.views.CategoryView;

import static dsdmsa.utmnews.This.networkComponent;

public class CategoryPresenter implements GetCategoryPresenter {

    @Inject
    UtmController controller;

    private CategoryView view;
//    private OnDataLoaded<List<Category>> onDataArrived = new OnDataLoaded<List<Category>>() {
//        @Override
//        public void onDatatLoaddedSuccesfull(List<Category> response) {
//            view.showCategories(response);
//            view.hideProgressDialog();
//        }
//
//        @Override
//        public void onError(String errorMsg) {
//            view.showErrorMessage(errorMsg);
//            view.hideProgressDialog();
//        }
//    };

    public CategoryPresenter(CategoryView view) {
        this.view = view;
        networkComponent.inject(this);
    }

    @Override
    public void getcategorys() {
        view.showPregressDialog();
//        controller.getCategories(onDataArrived);
    }
}
