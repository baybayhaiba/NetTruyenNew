import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/presenter/home_test.dart';
import 'package:provider/provider.dart';
import 'package:flutter_nettruyen/provider/connect_native_provider.dart';

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  static const String router = "home";

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  ConnectNativeProvider get connect => context.read<ConnectNativeProvider>();

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () {
        NettruyenNavigation.pop(context);
        return Future.value(false);
      },
      child: Scaffold(
        appBar: AppBar(
            leading: IconButton(
                icon: const Icon(Icons.arrow_back),
                onPressed: () {
                  NettruyenNavigation.pop(context);
                }),
            title: const Text('Nettruyen')),
        body: Builder(
          builder: (context) {
            return Container(
              alignment: Alignment.center,
              child: Text("Agr : ${connect.agr} && Config : ${connect.config}",
                  textAlign: TextAlign.center),
            );

            switch (connect.agr?.initRouter) {
              case 'red':
                return Container(color: Colors.red);
              case 'green':
                return Container(color: Colors.green);
              case 'blue':
                return Container(color: Colors.blue);
              case 'home':
                return const HomeTest();
            }

            return Container(color: Colors.black);
          },
        ),
      ),
    );
  }
}
