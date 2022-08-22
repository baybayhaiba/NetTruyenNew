// GENERATED CODE - DO NOT MODIFY BY HAND

part of '../model/message.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Message _$MessageFromJson(Map<String, dynamic> json) => Message(
      text: json['text'] as String? ?? '',
      type: $enumDecodeNullable(_$MessageTypeEnumMap, json['type']) ??
          MessageType.text,
      data: json['data'],
      size: json['size'] as int? ?? 0,
      person: json['person'] == null
          ? null
          : Person.fromJson(json['person'] as Map<String, dynamic>),
    )
      ..uuid = json['uuid'] as String
      ..dateTime = json['dateTime'] as String;

Map<String, dynamic> _$MessageToJson(Message instance) => <String, dynamic>{
      'uuid': instance.uuid,
      'dateTime': instance.dateTime,
      'text': instance.text,
      'size': instance.size,
      'data': instance.data,
      'type': _$MessageTypeEnumMap[instance.type],
      'person': instance.person,
    };

const _$MessageTypeEnumMap = {
  MessageType.text: 'text',
  MessageType.photo: 'photo',
  MessageType.video: 'video',
  MessageType.contact: 'contact',
  MessageType.music: 'music',
  MessageType.file: 'file',
};
