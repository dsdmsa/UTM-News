package dsdmsa.utm_news.network;


public interface OnDataLoaded<T> {
    void onDatatLoaddedSuccesfull(T response);
    void onError(String errorMsg);
}
