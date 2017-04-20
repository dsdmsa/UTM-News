package dsdmsa.utmnews.brodcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.models.enums.NetState;
import dsdmsa.utmnews.utils.Utils;

import static dsdmsa.utmnews.utils.Constants.IN_INTERNET_AVAIBLE;

public class ConnectionChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Utils.isOnline(context)) {
            EventBus.getDefault().post(NetState.ONLINE);
            App.getAppComponent().getPrefs().edit().putBoolean(IN_INTERNET_AVAIBLE, true).apply();
        }else {
            EventBus.getDefault().post(NetState.OFFLINE);
            App.getAppComponent().getPrefs().edit().putBoolean(IN_INTERNET_AVAIBLE, false).apply();
        }
    }

//    public boolean isOnline(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return (netInfo != null && netInfo.isConnected());
//    }

}