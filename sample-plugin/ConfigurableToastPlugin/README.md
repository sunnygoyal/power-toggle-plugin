# Configurable Toast Plugin

Power toggles plugin which shows a configurable toast message

### How it works
PowerToggles starts a configuration activity when the plugin is added on a widget. This activity (implemented by the plugin) takes care of the configuration and sends back an `id`. Its the plugin's responsibility to store all the settings associated with that `id`.

Every time the user clicks the toggle, the plugin is also given the `id` along with the requested state. Similarily the plugin can notify a state change for an `id`.

### How to create a configurable plugin

  - Get the [PowerTogglesPlugin.java](https://github.com/sunnygoyal/power-toggle-plugin/blob/master/api/com/painless/pc/PowerTogglesPlugin.java) file add it to you project source.
  - Create a new [BroadcastReceiver](http://developer.android.com/reference/android/content/BroadcastReceiver.html) that extends [PowerTogglesPlugin](https://github.com/sunnygoyal/power-toggle-plugin/blob/master/api/com/painless/pc/PowerTogglesPlugin.java)
  - Implement the `changeState(Context context, boolean newState, String id)` method. Make sure to call `sendStateUpdate()` in the end with the new state. 
  - Add the corresponding `<receiver>` tag to your `AndroidManifest.xml` file.
    * Add the intent filter for action `com.painless.pc.ACTION_SET_STATE`
    * Add the following meta tag in the receiver definition
    ```
    <meta-data
        android:name="com.painless.pc.CONFIG"
        android:value="configuration-activity-name" />
    ```
    * Define a label and icon for the receiver.
    * Optionally add a permission attribute (`android:permission="com.painless.pc.permission.CONTROL_PLUGIN"`) to the receiver so that only Power Toggles can call the receiver.

  - Define a configuration activity
    * In the menifest delaration, make sure
        1. `android:exported="true"`
        2. Add the following intent filter
        ```
        <intent-filter>
            <category android:name="android.intent.category.DEFAULT" />
        </intent-filter>
        ```

    * In the activity, generate a random string id (or reuse an existing id) & set the id in result before `finish()`
    ```
    setResult(RESULT_OK, new Intent().putExtra(Intent.EXTRA_UID, id));
    ```

