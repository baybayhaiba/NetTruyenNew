import 'package:flutter/material.dart';

class RouterUtil {
  RouterUtil._();
  static pushRoute(
      {required BuildContext context,
      required String screen,
      Object? argument}) {
    Navigator.of(context).pushNamed(screen, arguments: argument);
  }
}
