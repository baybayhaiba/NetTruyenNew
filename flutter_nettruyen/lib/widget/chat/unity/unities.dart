import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/widget/chat/unity/icon_game.dart';
import 'package:file_picker/file_picker.dart';
import 'icon_chat.dart';

enum TypeIcon { icon, game, video, photo, music, camera }

class Utilities extends StatefulWidget {
  const Utilities({Key? key, this.onTop, this.shouldDismissWhenPushToIntent})
      : super(key: key);
  final Function()? shouldDismissWhenPushToIntent;
  final Function(TypeIcon, String)? onTop;

  @override
  State<Utilities> createState() => _UtilitiesState();
}

class _UtilitiesState extends State<Utilities> {
  int indexSelected = 0;

  final List<String> icons = [
    'assets/icon/icon.png',
    'assets/icon/game.png',
    'assets/icon/ic_camera.png',
    'assets/icon/ic_file.png',
  ];

  Widget cardUnity(int index) => GestureDetector(
        onTap: () => setState(() {
          indexSelected = index;
        }),
        child: Container(
          height: 80,
          width: 80,
          decoration: BoxDecoration(
              color: indexSelected == index
                  ? Colors.lightBlueAccent
                  : Colors.black12,
              borderRadius: const BorderRadius.all(Radius.circular(16))),
          padding: const EdgeInsets.all(16),
          child: Image.asset(
            icons[index],
          ),
        ),
      );

  Widget unityDetail() {
    switch (indexSelected) {
      case 0:
        return IconChat(onTap: widget.onTop);
      case 3:
        intentFile();
        resetUnity;
        break;
      default:
        return IconGame(
          onTap: widget.onTop,
        );
    }

    return const SizedBox();
  }

  void get resetUnity => setState(() {
        indexSelected = 0;
        widget.shouldDismissWhenPushToIntent?.call();
      });

  void intentFile() async {
    FilePickerResult? result = await FilePicker.platform.pickFiles();

    if (result?.count != 0) {
      widget.onTop?.call(TypeIcon.camera, result?.files.first.path ?? '');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        SizedBox(
          height: 80,
          child: ListView.separated(
              scrollDirection: Axis.horizontal,
              itemBuilder: (context, index) => cardUnity(index),
              separatorBuilder: (ctx, index) => const SizedBox(
                    width: 8,
                  ),
              itemCount: icons.length),
        ),
        const SizedBox(
          height: 8,
        ),
        unityDetail(),
      ],
    );
  }
}
