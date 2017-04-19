package dsdmsa.utmnews.models.enums;


import android.graphics.Color;

import dsdmsa.utmnews.R;

public enum NetState {
    ONLINE(Color.GREEN, R.string.online_state,R.string.s_action_online),
    OFFLINE(Color.RED, R.string.offline_state, R.string.s_action_ofline);

    private int color;
    private int net_state;
    private int s_action;

    NetState(int color, int net_state, int s_action) {
        this.color = color;
        this.net_state = net_state;
        this.s_action = s_action;
    }

    public int getColor() {
        return color;
    }

    public int getS_action() {
        return s_action;
    }

    public int getNet_state() {
        return net_state;
    }
}
