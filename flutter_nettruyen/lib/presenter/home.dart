import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/foundation/key.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter_nettruyen/main.dart';

class Home extends StatelessWidget {
  const Home({Key? key}) : super(key: key);

  static const String router = "home";

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () {
        callToNative(command: CommandType.exit);
        return Future.value(false);
      },
      child: Scaffold(
        appBar: AppBar(
            title: Text("Nettruyen"),
            leading: IconButton(
                icon: const Icon(Icons.arrow_back),
                onPressed: () => callToNative(command: CommandType.exit))),
        body: Container(
          alignment: Alignment.center,
          child: Text('Hacker lo~'),
        ),
      ),
    );
  }
}
