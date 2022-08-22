import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/model/person.dart';
import 'package:flutter_nettruyen/screen/chat_screen.dart';
import 'package:uuid/uuid.dart';

class CreateUser extends StatefulWidget {
  const CreateUser({Key? key}) : super(key: key);

  @override
  State<CreateUser> createState() => _CreateUserState();
}

class _CreateUserState extends State<CreateUser> {
  final TextEditingController _controller = TextEditingController();
  int _selectIndex = 0;

  List<String> paths = [
    'assets/image/chibi_one.png',
    'assets/image/chibi_two.png',
    'assets/image/chibi_three.png'
  ];

  @override
  Widget build(BuildContext context) {
    final size = Size((MediaQuery.of(context).size.width / 3) - 24,
        (MediaQuery.of(context).size.width / 3) - 24);

    return SafeArea(
      child: Scaffold(
        body: Container(
          padding: const EdgeInsets.all(16),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
              children: [
            TextField(
              controller: _controller,
              decoration: const InputDecoration(
                  border: UnderlineInputBorder(), hintText: 'Please input name...'),
            ),
            const SizedBox(height: 32),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                _image(path: paths[0], index: 0, size: size),
                const SizedBox(width: 16),
                _image(path: paths[1], index: 1, size: size),
                const SizedBox(width: 16),
                _image(path: paths[2], index: 2, size: size)
              ],
            ),
            const SizedBox(height: 32),
            Row(
              children: [
                Expanded(
                    child: ElevatedButton(onPressed: () {}, child: Text('Hủy'))),
                const SizedBox(width: 32),
                Expanded(
                    child: ElevatedButton(
                        onPressed: () {
                          PersonPreference.writePersonToDisk(getValue());

                          Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => const ChatScreen()),
                          );
                        },
                        child: const Text('Đồng ý'))),
              ],
            )
          ]),
        ),
      ),
    );
  }

  Person getValue() {
    return Person(const Uuid().v4(), _controller.text, paths[_selectIndex]);
  }

  Widget _image(
      {required String path, required int index, required Size size}) {
    return GestureDetector(
      onTap: () => setState(() {
        _selectIndex = index;
      }),
      child: kIsWeb
          ? _createUserWeb(index, path)
          : _createUserMobile(index, path, size),
    );
  }

  Widget _createUserMobile(int index, String path, Size size) {
    return GestureDetector(
      onTap: () => setState(() {
        _selectIndex = index;
      }),
      child: Container(
        height: size.height,
        width: size.width,
        padding: const EdgeInsets.all(8),
        decoration: BoxDecoration(
            color:
                index == _selectIndex ? Colors.lightBlueAccent : Colors.white,
            borderRadius: BorderRadius.circular(16)),
        child: Container(
            decoration: BoxDecoration(
                color: Colors.blue,
                shape: BoxShape.circle,
                image: DecorationImage(image: AssetImage(path)))),
      ),
    );
  }

  Widget _createUserWeb(int index, String path) {
    return IntrinsicHeight(
      child: Container(
          padding: const EdgeInsets.all(8),
          decoration: BoxDecoration(
              color:
                  index == _selectIndex ? Colors.lightBlueAccent : Colors.white,
              borderRadius: BorderRadius.circular(16)),
          child: FractionallySizedBox(
              heightFactor: 0.8, child: Image.asset(path))),
    );
  }
}
