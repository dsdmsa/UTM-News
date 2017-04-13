package dsdmsa.utmnews.mvp;


public interface LoadingView {
    void showProgressDialog();

    void hideProgressDialog();

    void showInfoMessage(String errorMsg);
}
