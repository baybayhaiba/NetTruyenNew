import 'dart:developer';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter/src/foundation/key.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter_nettruyen/main.dart';
import 'package:flutter_nettruyen/provider/connect_native_provider.dart';

class HomeTest extends StatefulWidget {
  const HomeTest({Key? key}) : super(key: key);

  static const String router = "HomeTest";

  @override
  State<HomeTest> createState() => _HomeTestState();
}

class _HomeTestState extends State<HomeTest> {
  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () {
        NettruyenNavigation.pop(context);
        return Future.value(false);
      },
      child: Scaffold(
        appBar: AppBar(
            title: Text("${context.read<ConnectNativeProvider>().agr}"),
            leading: IconButton(
                icon: const Icon(Icons.arrow_back),
                onPressed: () {
                  NettruyenNavigation.pop(context);
                })),
        body: Container(
          alignment: Alignment.center,
          child: Text(
              'Languages : ${context.read<ConnectNativeProvider>().config?.language ?? 'Unknown'}'),
        ),
        bottomNavigationBar:
            Row(mainAxisAlignment: MainAxisAlignment.spaceAround, children: [
          IconButton(
              onPressed: () {
                context.read<ConnectNativeProvider>().startListenDemo(
                    ConnectNativeProvider.protocol_config,
                    """
{"languages" : "VietNam" , "theme" : "light"}
"""
                        .trim());
              },
              icon: const Icon(Icons.abc)),
          IconButton(
              onPressed: () {
                context.read<ConnectNativeProvider>().startListenDemo(
                    ConnectNativeProvider.protocol_config,
                    """
{"languages" : "USA" , "theme" : "dark"}
"""
                        .trim());
              },
              icon: const Icon(Icons.ac_unit_outlined)),
          IconButton(
              onPressed: () {
                context.read<ConnectNativeProvider>().startListenDemo(
                    ConnectNativeProvider.protocol_parameters,
                    """
{"functionName" : "BanhMiKepThit" , "jsonObject" : "banhsukem_proplayer"}
"""
                        .trim());
              },
              icon: const Icon(Icons.accessible_forward_outlined)),
        ]),
      ),
    );
  }
}
