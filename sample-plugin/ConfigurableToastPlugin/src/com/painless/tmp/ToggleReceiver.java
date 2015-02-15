package com.painless.tmp;

import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.painless.pc.PowerTogglesPlugin;

public class ToggleReceiver extends PowerTogglesPlugin {

    @Override
    protected void changeState(Context context, boolean newState, String id) {
        String msg = PreferenceManager.getDefaultSharedPreferences(context).getString(id, "Message not found");
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        sendStateUpdate(context, false, id);
    }
}
