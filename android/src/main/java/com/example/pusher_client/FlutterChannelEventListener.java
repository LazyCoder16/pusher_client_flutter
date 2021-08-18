package com.example.pusher_client;

import android.os.Handler;
import android.os.Looper;
import org.json.JSONObject;
import org.json.JSONException;
import com.pusher.client.channel.ChannelEventListener;
import com.pusher.client.channel.PusherEvent;

public class FlutterChannelEventListener implements ChannelEventListener {
  @Override
  public void onEvent(final PusherEvent event) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        JSONObject eventJson = new JSONObject();
        try {
          eventJson.put("channelName", event.getChannelName());
          eventJson.put("eventName", event.getEventName());
          eventJson.put("data", event.getData());
        } catch (JSONException ignored) {}
        PusherClientPlugin.eventSink.success(eventJson.toString());
      }
    });
  }

  @Override
  public void onSubscriptionSucceeded(String s) {}
}