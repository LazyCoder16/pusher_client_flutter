# pusher_client_flutter

Plugin for pusher client in dart. Only supports android at the moment. I created this plugin for my convenience and only contains functions which were important for my app. For a more complete libray visit [https://github.com/chinloyal/pusher_client](https://github.com/chinloyal/pusher_client). However this library doesn't support null safety which was the prime reason I couldn't use it. Any suggetions or additions would be appreciated :D.

## Example

Here is a small example of code which uses all the available functions (Useful for most use-cases):

```dart
Pusher pusher = new Pusher(appKey, cluster);
await pusher.connect(); // Connects to the pusher service
Channel channel = await pusher.subscribe(channelName);
await channel.bind(eventName, (String data) {
  print(data);
}); // Callback recieves the data as a String when the event is trigerred

// Later in the code
await channel.unbind(eventName); // Stop listening to that event
await pusher.unsubscribe(channelName); // Stop listening to events in that channel
await pusher.disconnect(); // Disconnect from the pusher service
```
