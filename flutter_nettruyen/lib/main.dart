import 'dart:developer';
import 'dart:ui';
import 'package:flutter_nettruyen/provider/connect_native_provider.dart';
import 'package:provider/provider.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_nettruyen/presenter/home.dart';
import 'package:flutter_nettruyen/route/generate_route.dart';
import 'package:flutter_nettruyen/theme/theme_nettruyen.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(MultiProvider(providers: [
    ChangeNotifierProvider(create: (_) => ConnectNativeProvider())
  ], child: const MyApp()));
}

final RouteObserver<PageRoute> routeObserver = RouteObserver<PageRoute>();

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    context.read<ConnectNativeProvider>().startListen();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Consumer(
      builder:
          (BuildContext context, ConnectNativeProvider value, Widget? child) {
        return MaterialApp(
          key: UniqueKey(),
          initialRoute: Home.router,
          onGenerateRoute: GenerateRoute.generateRoute,
          navigatorObservers: [routeObserver],
          debugShowCheckedModeBanner: false,
          scrollBehavior: const ScrollBehavior().copyWith(
              dragDevices: {PointerDeviceKind.mouse, PointerDeviceKind.touch}),
          theme: value.config?.theme ?? NettruyenTheme.dark(),
        );
      },
    );
  }
}
