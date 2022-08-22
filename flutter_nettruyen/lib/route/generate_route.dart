import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/screen/call_audio_screen.dart';
import 'package:flutter_nettruyen/screen/camera_screen.dart';
import 'package:flutter_nettruyen/screen/chat_screen.dart';
import 'package:flutter_nettruyen/screen/home_sreen.dart';

import '../screen/call_video_screen.dart';

class GenerateRoute {
  static Route<dynamic>? generateRoute(RouteSettings settings) {
    //return MaterialPageRoute(builder: (context) => CameraExampleHome());

    switch (settings.name) {
      case HomeScreen.router:
        return MaterialPageRoute(builder: (context) => HomeScreen());
      case ChatScreen.router:
        return MaterialPageRoute(builder: (context) => ChatScreen());

      case CallAudioScreen.route:
        return MaterialPageRoute(builder: (context) => CallAudioScreen());

      case CallVideoScreen.route:
        return MaterialPageRoute(builder: (context) => CallVideoScreen());
    }

    return null;
  }
}
