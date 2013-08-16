ipackage com.painless.pc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class PowerTogglesPlugin extends BroadcastReceiver {

	// Intent send by PowerToggles asking for a state change
	public static final String ACTION_SET_STATE = "com.painless.pc.ACTION_SET_STATE";

  // Intent send to PowerToggles notifying it of state changes
	public static final String ACTION_STATE_CHANGED = "com.painless.pc.ACTION_STATE_CHANGED";

	// Intent extra denoting the state. Value must be a boolean.
	public static final String EXTRA_STATE = "state";

	// Must be set to the class name of the receiver, when informing PowerToggles
	// of a state change. 
	public static final String EXTRA_VARID = "varID";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (ACTION_SET_STATE.equals(intent.getAction())) {
			changeState(context, intent.getBooleanExtra(EXTRA_STATE, false));
		}
	}

	/**
	 * Called when the PowerToggles widget requests a state change because
	 * the user clicked the corresponding toggle.
	 */
	protected abstract void changeState(Context context, boolean newState);

	/**
	 * Updates the plugin state in PowerToggles widget.
	 */
	public final void sendStateUpdate(Context context, boolean newState) {
		sendStateUpdate(this.getClass(), newState, context);
	}

	/**
	 * Updates the plugin state in PowerToggles widget.
	 * @param pluginClass The receiver implementing the plugin. An application can
	 * define multiple plugins, with one receiver per plugin.
	 * @param newState
	 * @param context
	 */
	public static final void sendStateUpdate(Class<? extends PowerTogglesPlugin> pluginClass,
			boolean newState, Context context) {
		context.sendBroadcast(new Intent(ACTION_STATE_CHANGED)
			.putExtra(EXTRA_VARID, pluginClass.getName())
			.putExtra(EXTRA_STATE, newState));
	}
}

