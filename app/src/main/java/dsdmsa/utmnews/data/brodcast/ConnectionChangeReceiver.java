package dsdmsa.utmnews.data.brodcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.domain.models.enums.NetState;
import dsdmsa.utmnews.domain.utils.Utils;

import static dsdmsa.utmnews.domain.utils.Constants.IN_INTERNET_AVAIBLE;

public class ConnectionChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Utils.isOnline(context)) {
            EventBus.getDefault().post(NetState.ONLINE);
            App.getAppComponent().getPrefs().edit().putBoolean(IN_INTERNET_AVAIBLE, true).apply();
        } else {
            EventBus.getDefault().post(NetState.OFFLINE);
            App.getAppComponent().getPrefs().edit().putBoolean(IN_INTERNET_AVAIBLE, false).apply();
        }
    }

}