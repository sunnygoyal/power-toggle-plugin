package com.painless.smp;

import static android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED;
import static android.telephony.TelephonyManager.EXTRA_STATE;
import static android.telephony.TelephonyManager.EXTRA_STATE_OFFHOOK;
import static android.telephony.TelephonyManager.EXTRA_STATE_RINGING;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;

public class SpeakerService extends Service {

	public static final String STOP_INTENT = "com.painless.smp.STOP_SERVICE";
	private static final int NOTIFICATION_ID = 10;

	public static boolean sIsRunning = false;

	private NotificationManager mNotificationManager;
	private AudioManager mAudioManager;

	private final BroadcastReceiver mCallReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String stateExtra = intent.getStringExtra(EXTRA_STATE);
			if (EXTRA_STATE_OFFHOOK.equals(stateExtra) || EXTRA_STATE_RINGING.equals(stateExtra)) {
				mAudioManager.setSpeakerphoneOn(true);

				new Thread() {

					@Override
					public void run() {
						try {
							Thread.sleep(500L);
						} catch (InterruptedException e) {
							// Ignore
						}
						mAudioManager.setSpeakerphoneOn(true);

					}
				}.run();
			}
		}
	};

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		super.onCreate();
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

		registerReceiver(mCallReceiver, new IntentFilter(ACTION_PHONE_STATE_CHANGED));

		Notification n = new Notification();
		n.icon = R.drawable.notify_speaker_icon;
		n.setLatestEventInfo(this, getString(R.string.speaker_mode_running),
				getString(R.string.click_to_exit),
				PendingIntent.getBroadcast(this, 0, new Intent(STOP_INTENT), 0));
		n.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
		mNotificationManager.notify(NOTIFICATION_ID, n);

		// Update the widget with state=true
		ToggleReceiver.sendStateUpdate(ToggleReceiver.class, true, this);
		sIsRunning = true;
	}

	@Override
	public void onDestroy() {
		sIsRunning = false;
		mNotificationManager.cancel(NOTIFICATION_ID);
		unregisterReceiver(mCallReceiver);

		// Update the widget with state=false
		ToggleReceiver.sendStateUpdate(ToggleReceiver.class, false, this);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
