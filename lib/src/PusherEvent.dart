class PusherEvent {
  String channelName, eventName, data;

  PusherEvent(this.channelName, this.eventName, this.data);

  factory PusherEvent.fromJson(Map<String, dynamic> json) {
    return PusherEvent(
      json["channelName"]!,
      json["eventName"]!,
      json["data"]!
    );
  }
}
