// ignore_for_file: public_member_api_docs, sort_constructors_first
import 'dart:convert';

class Agr {
  String? initRouter;
  String? functionName;
  dynamic jsonObject;

  Agr({
    this.initRouter,
    this.functionName,
    required this.jsonObject,
  });

  Agr copyWith({
    String? initRouter,
    String? functionName,
    dynamic? jsonObject,
  }) {
    return Agr(
      initRouter: initRouter ?? this.initRouter,
      functionName: functionName ?? this.functionName,
      jsonObject: jsonObject ?? this.jsonObject,
    );
  }

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'initRouter': initRouter,
      'functionName': functionName,
      'jsonObject': jsonObject,
    };
  }

  factory Agr.fromMap(Map<String, dynamic> map) {
    return Agr(
      initRouter:
          map['initRouter'] != null ? map['initRouter'] as String : null,
      functionName:
          map['functionName'] != null ? map['functionName'] as String : null,
      jsonObject: map['jsonObject'] as dynamic,
    );
  }

  String toJson() => json.encode(toMap());

  factory Agr.fromJson(String source) =>
      Agr.fromMap(json.decode(source) as Map<String, dynamic>);

  @override
  String toString() =>
      'Agr(initRouter: $initRouter, functionName: $functionName, jsonObject: $jsonObject)';

  @override
  bool operator ==(covariant Agr other) {
    if (identical(this, other)) return true;

    return other.initRouter == initRouter &&
        other.functionName == functionName &&
        other.jsonObject == jsonObject;
  }

  @override
  int get hashCode =>
      initRouter.hashCode ^ functionName.hashCode ^ jsonObject.hashCode;
}
