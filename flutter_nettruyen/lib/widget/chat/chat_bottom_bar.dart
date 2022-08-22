import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/screen/caro_screen.dart';
import 'package:flutter_nettruyen/widget/chat/unity/unities.dart';

class ChatBottomBar extends StatefulWidget {
  const ChatBottomBar({
    Key? key,
    required this.chatController,
    this.sendMessage,
    this.sendObject,
  }) : super(key: key);

  final TextEditingController chatController;
  final VoidCallback? sendMessage;
  final Function(String)? sendObject;

  @override
  State<ChatBottomBar> createState() => _ChatBottomBarState();
}

class _ChatBottomBarState extends State<ChatBottomBar> {
  final StreamController<void> _showUnity = StreamController();
  bool isShowUnity = false;

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
        onWillPop: () async {
          if (isShowUnity) {
            isShowUnity = false;
            _showUnity.sink.add(null);
            return false;
          }

          return true;
        },
        child: _chatBuilder());
  }

  Widget _chatBuilder() => StreamBuilder(
        stream: _showUnity.stream,
        builder: (context, snapshot) {
          return Column(
            children: [
              _chat(),
              const SizedBox(height: 8),
              AnimatedContainer(
                height: isShowUnity ? 300 : 0,
                duration: const Duration(milliseconds: 200),
                child: SingleChildScrollView(
                  child: Utilities(
                      key: UniqueKey(),
                      shouldDismissWhenPushToIntent: _showUtility,
                      onTop: (type, value) => _onClickAppendIcon(
                          typeIcon: type, value: value, context: context)),
                ),
              ),
            ],
          );
        },
      );

  void _onClickAppendIcon(
      {required TypeIcon typeIcon,
      required String value,
      required BuildContext context}) async {
    if (typeIcon == TypeIcon.icon) {
      widget.chatController.text = '${widget.chatController.text} $value ';
      widget.chatController.selection =
          TextSelection.collapsed(offset: widget.chatController.text.length);
    } else if (typeIcon == TypeIcon.game) {
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => const CaroScreen()),
      );
    } else {
      widget.sendObject?.call(value);
    }
  }

  Widget _chat() {
    return Container(
      decoration: BoxDecoration(
          color: const Color.fromARGB(255, 2, 12, 25),
          borderRadius: BorderRadius.circular(40)),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          const SizedBox(
            width: 10,
          ),
          _utility(),
          Expanded(
            child: TextField(
              style: const TextStyle(color: Colors.white,fontWeight: FontWeight.bold),
              controller: widget.chatController,
              textInputAction: TextInputAction.send,
              onSubmitted: (value) => widget.sendMessage?.call(),
              decoration:  InputDecoration(
                  hintText: 'Please input text...',
                  hintStyle: TextStyle(color: Colors.green[800]),
                  border: InputBorder.none),
            ),
          ),
          _send(),
          const SizedBox(
            width: 10,
          ),
        ],
      ),
    );
  }

  void _showUtility() {
    isShowUnity = !isShowUnity;
    _showUnity.sink.add(null);
  }

  Widget _utility() {
    return Material(
      color: const Color.fromARGB(255, 2, 12, 25),
      child: InkWell(
        onTap: _showUtility,
        child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Icon(
            !isShowUnity
                ? Icons.add_circle_outline
                : Icons.remove_circle_outline,
            color: Colors.blue,
            size: 24,
          ),
        ),
        splashColor: Colors.grey,
        borderRadius: const BorderRadius.all(Radius.circular(32)),
      ),
    );
  }

  Widget _send() {
    return Material(
      color: const Color.fromARGB(255, 2, 12, 25),
      child: InkWell(
        onTap: widget.sendMessage,
        child: const Padding(
          padding: EdgeInsets.all(8.0),
          child: Icon(
            Icons.send,
            color: Colors.blue,
            size: 24,
          ),
        ),
        splashColor: Colors.grey,
        borderRadius: const BorderRadius.all(Radius.circular(32)),
      ),
    );
  }
}
