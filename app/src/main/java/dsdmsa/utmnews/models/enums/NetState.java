package dsdmsa.utmnews.models.enums;


import android.graphics.Color;

import dsdmsa.utmnews.R;

public enum NetState {
    ONLINE(Color.GREEN,Color.RED, R.string.online_state,R.string.s_action_online),
    OFFLINE(Color.RED, Color.GREEN, R.string.offline_state, R.string.s_action_ofline);

    private int bkgColor;
    private int textColor;
    private int state;
    private int actionText;

    NetState(int bkgColor, int textColor, int state, int actionText) {
        this.bkgColor = bkgColor;
        this.textColor = textColor;
        this.state = state;
        this.actionText = actionText;
    }

    public int getBkgColor() {
        return bkgColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getState() {
        return state;
    }

    public int getActionText() {
        return actionText;
    }
}
