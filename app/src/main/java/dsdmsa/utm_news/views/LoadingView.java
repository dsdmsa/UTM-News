package dsdmsa.utm_news.views;


public interface LoadingView {
    void showPregressDialog();
    void hideProgressDialog();
    void showErrorMessage(String errorMsg);
}
