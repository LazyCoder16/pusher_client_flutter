package com.example.pusher_client;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.EventChannel.EventSink;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;

/** PusherClientPlugin */
public class PusherClientPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel methodChannel;
  public static EventSink eventSink;
  private Pusher pusher;
  private final String CHANNEL = "lazycoder16/pusher_client";
  private final String EVENT_STREAM = "lazycoder16/pusher_client_stream";
  //private ChannelEventListener channelEventListener;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    methodChannel = new MethodChannel(
      flutterPluginBinding.getBinaryMessenger(), 
      CHANNEL
    );
    methodChannel.setMethodCallHandler(this);
    
    new EventChannel(
      flutterPluginBinding.getBinaryMessenger(),
      EVENT_STREAM
    ).setStreamHandler(new EventChannel.StreamHandler() {
      @Override
      public void onListen(Object args, EventSink eventSink) {
        PusherClientPlugin.eventSink = eventSink;
      }

      @Override
      public void onCancel(Object args) { }
    });
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("initializePusher")) {
      String appKey = call.argument("appKey");
      String cluster = call.argument("cluster");
      initializePusher(appKey, cluster);
      result.success(0);
    } else if (call.method.equals("disconnect")) {
      disconnect();
      result.success(0);
    } else if (call.method.equals("subscribeToChannel")) {
      String channelName = call.argument("channelName");
      subscribeToChannel(channelName);
      result.success(0);
    } else if (call.method.equals("unsubscribeFromChannel")) {
      String channelName = call.argument("channelName");
      unsubscribeFromChannel(channelName);
      result.success(0);
    } else if (call.method.equals("bind")) {
      String channelName = call.argument("channelName");
      String eventName = call.argument("eventName");
      bind(channelName, eventName);
      result.success(0);
    } else if (call.method.equals("unbind")) {
      String channelName = call.argument("channelName");
      String eventName = call.argument("eventName");
      unbind(channelName, eventName);
      result.success(0);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
  }

  void initializePusher(String appKey, String cluster) {
    PusherOptions options = new PusherOptions();
    options.setCluster(cluster);
    pusher = new Pusher(appKey, options);
    pusher.connect();
  }

  void disconnect() {
    pusher.disconnect();
  }

  void subscribeToChannel(String channelName) {
    pusher.subscribe(channelName);
  }

  void unsubscribeFromChannel(String channelName) {
    pusher.unsubscribe(channelName);
  }

  void bind(String channelName, String eventName) {
    Channel channel = pusher.getChannel(channelName);
    channel.bind(eventName, new FlutterChannelEventListener());
  }

  void unbind(String channelName, String eventName) {
    Channel channel = pusher.getChannel(channelName);
    channel.unbind(eventName, new FlutterChannelEventListener());
  }
}
