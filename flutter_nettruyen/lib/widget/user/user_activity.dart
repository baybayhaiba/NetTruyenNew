import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/screen/chat_screen.dart';
import 'package:flutter_nettruyen/utils/router.dart';

class UserActivity extends StatelessWidget {
  const UserActivity({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () =>
          RouterUtil.pushRoute(context: context, screen: ChatScreen.router),
      child: Container(
        height: 70,
        decoration: const BoxDecoration(
            gradient: LinearGradient(
                begin: Alignment(-1.0, -4.0),
                end: Alignment(1.0, 4.0),
                colors: [
                  Color(0xFF5bc6ff),
                  Color(0xFF4da7db),
                ]),
            borderRadius: BorderRadius.all(Radius.circular(10)),
            boxShadow: [
              // BoxShadow(
              //     color: Color(0xFF051367),
              //     offset:  Offset(5.0, 5.0),
              //     blurRadius: 15.0,
              //     spreadRadius: 1.0),
              //  BoxShadow(
              //     color:  Color(0xFF5ecdff),
              //     offset: Offset(-5.0, -5.0),
              //     blurRadius: 15.0,
              //     spreadRadius: 1.0),
            ]),
        child: Center(
          child: ListTile(
            leading: ClipRRect(
              borderRadius: BorderRadius.circular(60 / 2),
              child: const Image(
                image: AssetImage('assets/image/chibi_two.png'),
                height: 44,
                width: 44,
                fit: BoxFit.cover,
              ),
            ),
            trailing: const Text(
              '12:00',
              style: TextStyle(color: Colors.white),
            ),
            title: const Text(
              'Pham Thanh Huy',
              style: TextStyle(
                  fontSize: 15,
                  color: Colors.white,
                  fontWeight: FontWeight.bold),
            ),
            subtitle: const Text(
              'CoDER proVIP 123',
              style: TextStyle(color: Colors.white),
            ),
          ),
        ),
      ),
    );
  }
}
