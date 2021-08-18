import 'package:flutter/services.dart';
import 'package:pusher_client/src/CallbackRegistry.dart';
import 'package:pusher_client/src/Channel.dart';

class Pusher {
  String appKey, cluster;
  static const MethodChannel platform = const MethodChannel('lazycoder16/pusher_client');
  static const stream = const EventChannel('lazycoder16/pusher_client_stream');
  Pusher(this.appKey, this.cluster);

  Future<void> connect() async {
    await platform.invokeMethod("initializePusher", { 
      "appKey": appKey, 
      "cluster": cluster 
    });
    CallbackRegistry.intialize();
    print("Successfully connected to pusher service");
  }

  Future<void> disconnect() async {
    await platform.invokeMethod("disconnect");
    print("Disconnected from pusher service");
  }

  Future<Channel> subscribe(String channelName) async {
    await platform.invokeMethod("subscribeToChannel", {
      "channelName": channelName
    });
    print("Subscribed to channel $channelName");
    return Channel(channelName);
  }

  Future<void> unsubscribe(String channelName) async {
    await platform.invokeMethod("unsubscribeFromChannel", {
      "channelName": channelName
    });
    CallbackRegistry.registry.remove(channelName);
    print("Unsubscribed from channel $channelName");
  }
}
