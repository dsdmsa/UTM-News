package dsdmsa.utmnews.data.brodcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.domain.utils.Utils;

import static dsdmsa.utmnews.domain.utils.Constants.IN_INTERNET_AVAIBLE;

public class ConnectionChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Utils.isOnline(context)) {
            App.getAppComponent().getPrefs().edit().putBoolean(IN_INTERNET_AVAIBLE, true).apply();
        } else {
            App.getAppComponent().getPrefs().edit().putBoolean(IN_INTERNET_AVAIBLE, false).apply();
        }
    }

}