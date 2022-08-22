import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/model/message.dart';
import 'package:flutter_nettruyen/widget/chat/item/contact.dart';
import 'package:flutter_nettruyen/widget/chat/item/file.dart';
import 'package:flutter_nettruyen/widget/chat/item/music.dart';
import 'package:flutter_nettruyen/widget/chat/item/photo.dart';
import 'package:flutter_nettruyen/widget/chat/item/text.dart';
import 'package:flutter_nettruyen/widget/chat/item/video.dart';

import '../../constants/layout_constants.dart';

class FactoryChat {
  static Widget type(Message message) {
    Widget? widgetChild;
    switch (message.type) {
      case MessageType.text:
        widgetChild = ChatText(
          content: message.text,
          isSender: message.isSender,
        );
        break;
      case MessageType.photo:
        widgetChild = PhotoChat(message: message,);
        break;
      case MessageType.video:
        widgetChild = VideoChat();
        break;
      case MessageType.contact:
        widgetChild = ContactChat();
        break;
      case MessageType.music:
        widgetChild = MusicChat();
        break;
      case MessageType.file:
        widgetChild = FileChat();
        break;
    }

    return background(message, widgetChild: widgetChild);
  }

  static background(Message message, {Widget? widgetChild}) {

    return Column(
        crossAxisAlignment: message.isSender
            ? CrossAxisAlignment.end
            : CrossAxisAlignment.start,
        children: [
          Container(
            padding:
                const EdgeInsets.all(12),
            decoration: BoxDecoration(
              borderRadius: BorderRadius.only(
                  topRight: const Radius.circular(8),
                  topLeft: Radius.circular(message.isSender ? 8 : 0),
                  bottomLeft: const Radius.circular(8),
                  bottomRight: Radius.circular(message.isSender ? 0 : 8)
              ),
              color:
                  !message.isSender ? Colors.white : primary,
            ),
            child: widgetChild,
          )
        ]);
  }
}
