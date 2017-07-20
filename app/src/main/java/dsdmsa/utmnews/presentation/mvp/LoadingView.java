package dsdmsa.utmnews.presentation.mvp;


public interface LoadingView {
    void showProgressDialog();

    void hideProgressDialog();

    void showInfoMessage(String errorMsg);
}
