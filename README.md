# Plugin API for Power Toggles

[Install App from Google Play](https://play.google.com/store/apps/details?id=com.painless.pc)

Power Toggles is an android widget, similar to the native Power Control widget, with advanced customization features. The app provides some 40+ toggle options.
Plugins for the can add to the list of available toggle functions.

### How to create a plugin
  - Get the [PowerTogglesPlugin.java](https://github.com/sunnygoyal/power-toggle-plugin/blob/master/api/com/painless/pc/PowerTogglesPlugin.java) file add it to you project source.
  - Create a new [BroadcastReceiver](http://developer.android.com/reference/android/content/BroadcastReceiver.html) that extends [PowerTogglesPlugin](https://github.com/sunnygoyal/power-toggle-plugin/blob/master/api/com/painless/pc/PowerTogglesPlugin.java)
  - Implement the `changeState()` method. Make sure to call `sendStateUpdate()` in the end with the new state. 
  - Add the corresponding `<receiver>` tag to your `AndroidManifest.xml` file.
    * Add the intent filter for action `com.painless.pc.ACTION_SET_STATE`
    * Define a label and icon for the receiver.
    * Optionally add a permission attribute (`android:permission="com.painless.pc.permission.CONTROL_PLUGIN"`) to the receiver so that only Power Toggles can call the receiver.

### How it works
When a user click the toggle button, the app calls the corresponding receiver with the desired new state. The state of the toggle is set to busy.
Once the plugin finishes its processing, it calls `sendStateUpdate` to notify Power Toggles that the processing is complete and sends the new state of the plugin. The new state is same as the desired state in most cases, but in can be different if the plugin failed in the task for some reason.

The plugin **must** call `sendStateUpdate` once the processing is done or the toggle will keep showing the busy state.
Power Toggles will try to ensures that the `changeState` is called serially, so that another `changeState` is not called while the toggle is showing busy.

Plugins can notify Power Toggles of a state change due to some external action (like changes made on a different UI) by appropriately calling the `sendStateUpdate` method.

An app can also define multiple plugins, by defining multiple receiver as described in the above section. When calling `sendStateUpdate` (static implementation) from a different class, make sure to pass the reference of the right receiver class

### Icon Guidelines
A receiver must define a plugin and a label. The label is used as the plugin name, and the icon is used as the default icon for the toggle. The user has the option to change the icon.

The icon should use flat white color foreground, and transparent background. Any color is automatically removed from the icon and the widget theme color is applied based on the toggle state. The image should be square shaped and provide around 12.5% empty space on all sides, to go well with the default icon scheme of the app.
