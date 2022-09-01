import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/presenter/home.dart';

class GenerateRoute {
  static Route<dynamic>? generateRoute(RouteSettings settings) {
    //return MaterialPageRoute(builder: (context) => CameraExampleHome());

    switch (settings.name) {
      case Home.router:
        return MaterialPageRoute(builder: (context) => const Home());
    }

    return null;
  }
}
