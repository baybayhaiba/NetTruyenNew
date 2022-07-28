import 'dart:developer';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_nettruyen/model/person.dart';
import 'package:flutter_nettruyen/route/generate_route.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_nettruyen/screen/home_sreen.dart';
import 'package:flutter_nettruyen/socket/socket_io.dart';

import 'constants/layout_constants.dart';

//List<CameraDescription> cameras = [];

Future<void> _getDocuments() async {
  MethodChannel _methodChannel =
      const MethodChannel('flutter/MethodChannelDemo');
  List<dynamic> documentList = [""];
  try {
    documentList = await _methodChannel.invokeMethod("Documents");
  } on PlatformException {
    print("exceptiong");
  }

  documentList.forEach((document) {
    print("Document: $document"); // seach in Logcat "Document"
  });
}

Future<void> main() async {
  //Fetch the available cameras before initializing the app.
  // try {
  //   WidgetsFlutterBinding.ensureInitialized();
  //   cameras = await availableCameras();
  // } on CameraException catch (e) {
  //   print('Error in fetching the cameras: $e');
  // }
  WidgetsFlutterBinding.ensureInitialized();
  PersonPreference.sharedPreferences = await SharedPreferences.getInstance();
  SocketIoServer.initializeSocket(onConnect: (uuid) => log("hello $uuid"));
  runApp(const MyApp());
}

final RouteObserver<PageRoute> routeObserver = RouteObserver<PageRoute>();

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  Person? person = PersonPreference.getInstance();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      initialRoute: HomeScreen.router,
      onGenerateRoute: GenerateRoute.generateRoute,
      navigatorObservers: [routeObserver],
      debugShowCheckedModeBanner: false,
      scrollBehavior: const ScrollBehavior().copyWith(
          dragDevices: {PointerDeviceKind.mouse, PointerDeviceKind.touch}),
      title: 'Flutter Demo',
      theme: ThemeData(
        primaryColor: primary,
      ),
    );
  }
}
