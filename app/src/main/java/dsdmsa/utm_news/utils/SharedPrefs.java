package dsdmsa.utm_news.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private static final String AUTH_TOKEN = "utm.app.AUTH_TOKEN";
    private static final String SHARED_PREFERENCES_NAME = "utm.app";
    private static final String USER = SHARED_PREFERENCES_NAME + "USER";
    private final SharedPreferences settings;

    public SharedPrefs(Context context) {
        settings = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void setAuthToken(String authToken) {
        settings.edit().putString(AUTH_TOKEN, authToken).apply();
    }

    public String getToken() {
        if (settings != null)
        return settings.getString(AUTH_TOKEN, null);
        return null;
    }

    public void deleteToken() {
        settings.edit().remove(AUTH_TOKEN).apply();
    }
//
//    public void saveUser(User user) {
//        try {
//            settings.edit().putString(USER, JsonMarshaller.toJson(user)).apply();
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//        }
//    }
//
//    public User getUser() {
//        String jsonData = settings.getString(USER, null);
//        if (jsonData != null) {
//            try {
//                return JsonMarshaller.fromJson(User.class, jsonData);
//            } catch (Exception e) {
//                settings.edit().remove(USER).apply();
//                return null;
//            }
//        }
//        return null;
//    }
}