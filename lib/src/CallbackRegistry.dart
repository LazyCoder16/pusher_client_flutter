import 'dart:convert';

import 'package:pusher_client/pusher_client.dart';
import 'package:pusher_client/src/PusherEvent.dart';

class CallbackRegistry {
  static Map<String, Map<String, void Function(String)>> registry = new Map();

  static void intialize() {
    Pusher.stream.receiveBroadcastStream().listen((e) {
      PusherEvent event = PusherEvent.fromJson(jsonDecode(e));
      print("Trigerred $e");
      if (
          registry.containsKey(event.channelName) && 
          registry[event.channelName]!.containsKey(event.eventName)) {
        registry[event.channelName]![event.eventName]!(event.data);
      }
    });
  }

  static void register(String channelName, String eventName, void Function(String) callback) {
    if (!registry.containsKey(channelName)) {
      registry[channelName] = Map();
    }
    registry[channelName]![eventName] = callback;
  }

  static void unregister(String channelName, String eventName) {
    if (registry.containsKey(channelName)) {
      registry[channelName]!.remove(eventName);
    }
  }
}
