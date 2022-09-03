import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';
import 'package:flutter_nettruyen/model/agr.dart';
import 'package:flutter_nettruyen/model/config.dart';

enum Command { exit }

class NettruyenNavigation {
  static void pop(BuildContext context) =>
      context.read<ConnectNativeProvider>().pop();
}

class ConnectNativeProvider extends ChangeNotifier {
  static const methodChannel = 'flutter/MethodChannelDemo';
  static const protocol_parameters = "flutter_navigation";
  static const protocol_config = "flutter_config";

  ConfigNative? config;
  Agr? agr;

  get _methodChannel => const MethodChannel(methodChannel);

  void pop() => _methodChannel.invokeMethod(Command.exit.name);

  void startListenDemo(String functionName, String agrument) {
    if (protocol_parameters == functionName) {
      agr = Agr.fromJson(agrument);
    } else if (protocol_config == functionName) {
      config = ConfigNative.fromString(agrument);
    }

    notifyListeners();
  }

  void startListen() {
    const MethodChannel(methodChannel).setMethodCallHandler((call) async {
      print("hahahahahahaha => ${call.method} ---- ${call.arguments}");

      if (protocol_parameters == call.method) {
        agr = Agr.fromJson(call.arguments);
      } else if (protocol_config == call.method) {
        config = ConfigNative.fromString(call.arguments);
      }

      notifyListeners();
    });
  }
}
