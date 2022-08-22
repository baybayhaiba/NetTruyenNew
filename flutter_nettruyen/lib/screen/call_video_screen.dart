import 'package:flutter/material.dart';
import 'package:flutter_webrtc/flutter_webrtc.dart';

class CallVideoScreen extends StatefulWidget {
  const CallVideoScreen({Key? key}) : super(key: key);

  static const route = 'call_video_screen';

  @override
  State<CallVideoScreen> createState() => _CallVideoScreenState();
}

class _CallVideoScreenState extends State<CallVideoScreen> {
  final _localVideoRenderer = RTCVideoRenderer();

  void initRenderers() async {
    await _localVideoRenderer.initialize();
  }

  @override
  void initState() {
    initRenderers();
     _getUserMedia();
    super.initState();
  }

  @override
  void dispose() async{
    super.dispose();
         await _localVideoRenderer.dispose();
  }


  void _getUserMedia() async {
    final Map<String, dynamic> mediaConstraints = {
      'audio': true,
      'video': {
        'facingMode': 'user',
      }
    };
 
    MediaStream stream =
        await navigator.mediaDevices.getUserMedia(mediaConstraints);
    _localVideoRenderer.srcObject = stream;

    setState(() { });
  }
 

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Bánh mì kẹp thịt'),
      ),
      body: Stack(
        children: [
          Positioned(
              top: 0.0,
              right: 0.0,
              left: 0.0,
              bottom: 0.0,
              child: RTCVideoView(_localVideoRenderer))
        ],
      ),
    );
  }
}
