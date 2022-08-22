import 'dart:async';

import 'package:flutter/material.dart';

class Rectangle extends StatelessWidget {
  Rectangle({Key? key, required this.index, required this.size}) : super(key: key);
  ValueNotifier<String> characterObserver =ValueNotifier('');
  final String index;
  final Size size;

  void onClick(String character) {
    characterObserver.value = character;
  }

  @override
  Widget build(BuildContext context) {


    return ValueListenableBuilder(valueListenable: characterObserver, builder: (ctx,value,child){
      return Container(
        width: size.width,
        height: size.height,
        alignment: Alignment.center,
        decoration: BoxDecoration(border: Border.all(color: Colors.black)),
        child: Stack(
          children: [
            Text(value.toString(),style: const TextStyle(fontSize: 25,color: Colors.red),),
            Text(index,style: const TextStyle(fontSize: 8),)
          ],
        ),
      );
    });
  }
}
