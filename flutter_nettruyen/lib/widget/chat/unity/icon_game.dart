import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/widget/chat/unity/unities.dart';

class IconGame extends StatelessWidget {
  IconGame({Key? key, this.onTap}) : super(key: key);

  final Function(TypeIcon,String)? onTap;

  final List<String> icons = [
    'assets/image/caro.png',
  ];


  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Wrap(
            spacing: 12,
            runSpacing: 12,
            children: icons.map((value) => _item(value)).toList()),
      ],
    );
  }

  Widget _item(String value) {
    return Material(
      borderRadius: BorderRadius.circular(16),
      color: Colors.black12,
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: InkWell(
          onTap: () => onTap?.call(TypeIcon.game,value),
          child: Image.asset(value, height: 80, width: 80),
          borderRadius: const BorderRadius.all(Radius.circular(8)),
          splashColor: Colors.grey,
        ),
      ),
    );
  }
}
