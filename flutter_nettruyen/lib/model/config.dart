// ignore_for_file: public_member_api_docs, sort_constructors_first
import 'dart:convert';
import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/theme/theme_nettruyen.dart';

class ConfigNative {
  String? language;
  ThemeData? theme;
  ConfigNative({
    this.language,
    this.theme,
  });

  factory ConfigNative.fromString(String value) {
    Map data = jsonDecode(value);
    return ConfigNative(
        language: data['languages'],
        theme: data['theme'] == 'dark'
            ? NettruyenTheme.dark()
            : NettruyenTheme.light());
  }

  ConfigNative copyWith({
    String? language,
    ThemeData? theme,
  }) {
    return ConfigNative(
      language: language ?? this.language,
      theme: theme ?? this.theme,
    );
  }

  @override
  String toString() => 'ConfigNative(language: $language, theme: $theme)';

  @override
  bool operator ==(covariant ConfigNative other) {
    if (identical(this, other)) return true;

    return other.language == language && other.theme == theme;
  }

  @override
  int get hashCode => language.hashCode ^ theme.hashCode;
}
