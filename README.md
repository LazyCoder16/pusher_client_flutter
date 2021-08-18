# pusher_client_flutter

Plugin for pusher client in dart. Only supports android at the moment. I created this plugin for my convenience and only contains functions which were important for my app. For a more complete libray visit [https://github.com/chinloyal/pusher_client](https://github.com/chinloyal/pusher_client). However this library doesn't support null safety which was the prime reason I couldn't use it. Any suggetions or additions would be appreciated :D.

## Example

Here is a small example of code which uses all the available functions (Useful for most use-cases):

```dart
Pusher pusher = new Pusher(appKey, cluster);
pusher.connect(); // Connects to the pusher service
Channel channel = pusher.subscribe(channelName);
channel.bind(eventName, (String data) {
  print(data);
}); // Callback recieves the data as a String when the event is trigerred

// Later in the code
channel.unbind(eventName); // Stop listening to that event
pusher.unsubscribe(channelName); // Stop listening to events in that channel
pusher.disconnect(); // Disconnect from the pusher service
```
