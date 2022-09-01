import 'dart:developer';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_nettruyen/presenter/home.dart';
import 'package:flutter_nettruyen/route/generate_route.dart';
import 'package:flutter_nettruyen/theme/theme_nettruyen.dart';

enum CommandType { exit }

const String methodChannel = 'flutter/MethodChannelDemo';

Future<void> callToNative({required CommandType command}) async {
  MethodChannel _methodChannel = const MethodChannel(methodChannel);

  try {
    await _methodChannel.invokeMethod(command.name);
  } on PlatformException {
    print("exceptiong");
  }
}

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const MyApp());
}

final RouteObserver<PageRoute> routeObserver = RouteObserver<PageRoute>();

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String title = "Flutter Nettruyen";
  @override
  void initState() {
    const MethodChannel(methodChannel).setMethodCallHandler((call) async {
      setState(() {
        title = call.method;
      });
      print('${call.method} ---- ${call.arguments}');
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      initialRoute: Home.router,
      onGenerateRoute: GenerateRoute.generateRoute,
      navigatorObservers: [routeObserver],
      debugShowCheckedModeBanner: false,
      scrollBehavior: const ScrollBehavior().copyWith(
          dragDevices: {PointerDeviceKind.mouse, PointerDeviceKind.touch}),
      theme: NettruyenTheme.dark(),
    );
  }
}
