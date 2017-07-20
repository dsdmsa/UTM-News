package dsdmsa.utmnews.data.network;


public interface OnDataLoaded<T> {
    void onSuccess(T response);
    void onError(String errorMsg);
}
