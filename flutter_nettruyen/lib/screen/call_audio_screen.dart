import 'package:flutter/material.dart';

class CallAudioScreen extends StatefulWidget {
  const CallAudioScreen({Key? key}) : super(key: key);

  static const route = 'call_audio_screen';

  @override
  State<CallAudioScreen> createState() => _CallAudioScreenState();
}

class _CallAudioScreenState extends State<CallAudioScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        color: Colors.black,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [avatar(), const SizedBox(height: 64), action()],
        ),
      ),
    );
  }

  ClipRRect avatar() => ClipRRect(
        child: Image.asset(
          'assets/image/chibi_one.png',
          height: 200,
          width: 200,
          fit: BoxFit.cover,
        ),
        borderRadius: const BorderRadius.all(Radius.circular(999999)),
      );

  Widget action() => Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          imageAsset(path: 'assets/icon/ic_voice.png', color: Colors.white),
          imageAsset(
              path: 'assets/icon/ic_call_off.png',
              color: Colors.red,
              onPressed: () => Navigator.of(context).pop()),
          imageAsset(
              path: 'assets/icon/ic_call_video.png', color: Colors.green),
        ],
      );

  Widget imageAsset(
      {required String path, Color? color, VoidCallback? onPressed}) {
    return Material(
      color: Colors.transparent,
      child: InkWell(
        onTap: onPressed,
        borderRadius: BorderRadius.circular(16),
        splashColor: Colors.grey,
        child: Container(
          padding: const EdgeInsets.all(16),
          child: Image.asset(
            path,
            height: 36,
            width: 36,
            color: color,
          ),
        ),
      ),
    );
  }
}
