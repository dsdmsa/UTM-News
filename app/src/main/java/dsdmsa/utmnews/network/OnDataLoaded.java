package dsdmsa.utmnews.network;


public interface OnDataLoaded<T> {
    void onDatatLoaddedSuccesfull(T response);
    void onError(String errorMsg);
}
