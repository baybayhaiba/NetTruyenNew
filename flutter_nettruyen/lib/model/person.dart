import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:equatable/equatable.dart';
part '../part/person.g.dart';

@JsonSerializable()
class Person extends Equatable{
  final String uuid;
  final String username;
  final String avatar;

  const Person(this.uuid,this.username, this.avatar);

  factory Person.fromJson(Map<String,dynamic> json) => _$PersonFromJson(json);
  Map<String,dynamic> toJson() => _$PersonToJson(this);

  @override
  // TODO: implement props
  List<Object?> get props => [uuid];
}

extension PersonPreference on Person {

  static SharedPreferences? sharedPreferences;

  static Person? _instance;

  static Person? getInstance(){
    if(sharedPreferences == null || (sharedPreferences?.getString('person')?.isEmpty ?? true)) return null;

    final json = jsonDecode(sharedPreferences?.getString('person') ?? '');

    final person = Person.fromJson(json);

    _instance = person;

    return _instance;
  }

  static writePersonToDisk(Person person){
    sharedPreferences?.setString('person', jsonEncode(person.toJson()));
    _instance = person;
  }
}