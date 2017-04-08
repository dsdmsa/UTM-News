package dsdmsa.utmnews.network;


public interface OnDataLoaded<T> {
    void onSuccess(T response);
    void onError(String errorMsg);
}
