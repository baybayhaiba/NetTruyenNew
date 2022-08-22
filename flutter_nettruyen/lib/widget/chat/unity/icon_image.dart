import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/widget/chat/unity/unities.dart';
import 'package:file_picker/file_picker.dart';

class IconPicture extends StatefulWidget {
  const IconPicture({Key? key, this.onTap}) : super(key: key);

  final Function(TypeIcon, String)? onTap;

  @override
  State<IconPicture> createState() => _IconPictureState();
}

class _IconPictureState extends State<IconPicture> {
  final List<String> icons = [
    'assets/icon/ic_camera.png',
    'assets/image/image_one.jpg',
    'assets/image/image_two.jpg',
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
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: InkWell(
          onTap: () => widget.onTap?.call(
              value == 'assets/icon/ic_camera.png'
                  ? TypeIcon.camera
                  : TypeIcon.photo,
              value),
          child: ClipRRect(
              child:
                  Image.asset(value, height: 80, width: 80, fit: BoxFit.cover),
              borderRadius: const BorderRadius.all(Radius.circular(16))),
          borderRadius: const BorderRadius.all(Radius.circular(8)),
          splashColor: Colors.grey,
        ),
      ),
    );
  }
}
