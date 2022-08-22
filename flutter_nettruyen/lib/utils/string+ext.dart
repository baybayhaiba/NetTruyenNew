import 'package:flutter_nettruyen/model/message.dart';

const imageExtension = ['jpg', 'png'];

extension StringFile on String {
  MessageType getType() {
    final extension = split(".").last;

    if (imageExtension.contains(extension)) {
      return MessageType.photo;
    }

    return MessageType.file;
  }
}
