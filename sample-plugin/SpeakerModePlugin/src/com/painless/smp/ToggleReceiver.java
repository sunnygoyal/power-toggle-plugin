package com.painless.smp;

import com.painless.pc.PowerTogglesPlugin;

import android.content.Context;
import android.content.Intent;

public class ToggleReceiver extends PowerTogglesPlugin {

	@Override
	protected void changeState(Context context, boolean newState) {
		if (newState == SpeakerService.sIsRunning) {
			// No need to do anything. Just notify PowerToggles that the request is processed.
			sendStateUpdate(context, newState);
		} else if (newState) {
			// Start the service. The service will notify PowerToggles of the new state
			// once it has started
			context.startService(new Intent(context, SpeakerService.class));
		} else {
			// Stop the service. The service will notify PowerToggles of the new state
			// once it is about to stop.
			context.stopService(new Intent(context, SpeakerService.class));
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (SpeakerService.STOP_INTENT.equals(intent.getAction())) {
			// Stop service if the notification was clicked.
			context.stopService(new Intent(context, SpeakerService.class));
		}
		super.onReceive(context, intent);
	}
}
