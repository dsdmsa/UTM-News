package dsdmsa.utmnews.views;


public interface LoadingView {
    void showPregressDialog();
    void hideProgressDialog();
    void showErrorMessage(String errorMsg);
}
