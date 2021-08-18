import 'package:pusher_client/pusher_client.dart';
import 'package:pusher_client/src/CallbackRegistry.dart';

class Channel {
  String channelName;
  Channel(this.channelName);

  Future<void> bind(String eventName, void Function(String) callback) async {
    CallbackRegistry.register(channelName, eventName, callback);
    await Pusher.platform.invokeMethod("bind", {
      "channelName": channelName,
      "eventName": eventName
    });
  }

  Future<void> unbind(String eventName) async {
    CallbackRegistry.unregister(channelName, eventName);
    await Pusher.platform.invokeMethod("unbind", {
      "channelName": channelName,
      "eventName": eventName
    });
  }
}
