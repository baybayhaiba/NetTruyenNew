import 'package:flutter_nettruyen/model/person.dart';
import 'package:uuid/uuid.dart';
import 'package:json_annotation/json_annotation.dart';

part '../part/message.g.dart';

enum MessageType { text, photo, video, contact, music, file }

@JsonSerializable()
class Message {
  String uuid = const Uuid().v4();
  String dateTime = DateTime.now().toString();
  String text;
  int size;
  Object? data;
  MessageType type;
  Person? person;
  bool isSender;

  Message(
      {this.text = '',
      this.type = MessageType.text,
      this.data,
      this.size = 0,
      this.person})
      : isSender = person == PersonPreference.getInstance();

  factory Message.fromJson(Map<String, dynamic> json) =>
      _$MessageFromJson(json);

  Map<String, dynamic> toJson() => _$MessageToJson(this);
}
