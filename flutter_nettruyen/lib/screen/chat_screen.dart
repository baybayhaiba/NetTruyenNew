import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/model/message.dart';
import 'package:flutter_nettruyen/screen/call_audio_screen.dart';
import 'package:flutter_nettruyen/screen/call_video_screen.dart';
import 'package:flutter_nettruyen/utils/router.dart';
import 'package:flutter_nettruyen/widget/chat/chat_bottom_bar.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_nettruyen/widget/chat/factory.dart';
import '../bloc/chat/chat_bloc.dart';
import 'package:flutter_svg/flutter_svg.dart';

import '../constants/layout_constants.dart';

class ChatScreen extends StatefulWidget {
  const ChatScreen({Key? key}) : super(key: key);
  static const String router = 'chat_screen';

  @override
  State<ChatScreen> createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {
  final MessageBloc bloc = MessageBloc();

  final List<Message> messages = [];

  @override
  void initState() {
    bloc.add(InitializeMessageEvent());
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: appBar(),
        body: Container(
          padding: const EdgeInsets.all(16),
          child: Column(children: [
            Expanded(child: _blocBuilder()),
            const SizedBox(height: 8),
            _bottomChat()
          ]),
        ),
      ),
    );
  }

  Widget _bottomChat() => ChatBottomBar(
        chatController: bloc.chatController,
        sendMessage: () {
          bloc.add(SendMessageEvent(type: MessageType.text));
        },
        sendObject: (value) {
          bloc.add(SendMessageEvent(type: MessageType.file, path: value));
        },
      );

  AppBar appBar() => AppBar(
        title: const Text('Chat Simulator'),
        backgroundColor: primary,
        centerTitle: false,
        toolbarHeight: 70,
        actions: [
          TextButton(
            onPressed: () => RouterUtil.pushRoute(
                context: context, screen: CallVideoScreen.route),
            child: SvgPicture.asset(
              'assets/icon/ic_video.svg',
              height: 20,
              width: 20,
            ),
          ),
          TextButton(
            onPressed: () => RouterUtil.pushRoute(
                context: context, screen: CallAudioScreen.route),
            child: SvgPicture.asset(
              'assets/icon/ic_call.svg',
              height: 20,
              width: 20,
            ),
          ),
        ],
      );

  Widget _blocBuilder() => BlocBuilder<MessageBloc, MessageState>(
        bloc: bloc,
        builder: (event, state) => ListView.separated(
            itemBuilder: (context, index) =>
                FactoryChat.type(bloc.messages[index]),
            separatorBuilder: (ctx, index) => const SizedBox(height: 16),
            itemCount: bloc.messages.length),
      );
}
