// GENERATED CODE - DO NOT MODIFY BY HAND

part of '../model/person.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Person _$PersonFromJson(Map<String, dynamic> json) => Person(
      json['uuid'] as String,
      json['username'] as String,
      json['avatar'] as String,
    );

Map<String, dynamic> _$PersonToJson(Person instance) => <String, dynamic>{
      'uuid': instance.uuid,
      'username': instance.username,
      'avatar': instance.avatar,
    };
