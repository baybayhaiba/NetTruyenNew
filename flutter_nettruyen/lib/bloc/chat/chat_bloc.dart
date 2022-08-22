import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_nettruyen/utils/string+ext.dart';
import '../../constants/layout_constants.dart';
import '../../model/message.dart';
import '../../model/person.dart';
import '../../socket/socket_io.dart';

class MessageEvent {}

class MessageState {}

class InitializeMessageEvent extends MessageEvent {}

class SendMessageEvent extends MessageEvent {
  MessageType type;
  String? path;

  SendMessageEvent({required this.type, this.path});
}

class ReceiveMessageEvent extends MessageEvent {}

class UploadMessageState extends MessageState {}

class LoadingMessageState extends MessageState {}

class ErrorMessageState extends MessageState {}

class SuccessMessageState extends MessageState {}

class MessageBloc extends Bloc<MessageEvent, MessageState> {
  final List<Message> messages = [];
  late final SocketIoServer socket;

  final TextEditingController chatController = TextEditingController();

  MessageBloc() : super(LoadingMessageState()) {
    on<InitializeMessageEvent>((event, emit) {
      socket = SocketIoServer.getInstance();

      socket.observer(address: messageChanel).stream.listen((value) {
        _handleMessage(value);
      });
    });
    on<SendMessageEvent>((event, emit) {
      late final dynamic objectSend;

      if (event.type == MessageType.text) {
        objectSend = _sendText();
      } else {
        objectSend = _sendFile(path: event.path);
      }

      _handleMessage(objectSend);
    });
    on<ReceiveMessageEvent>((event, emit) {
      emit(UploadMessageState());
    });
  }

  void _handleMessage(Object? data) async {
    if (data == null) return;

    if (data is Future<Message?>) {
      final message = await data;
      if (message != null) {
        messages.add(message);
      } else {
        return;
      }
    } else if (data is Message) {
      messages.add(data);
    } else if (data is String) {
      messages.add(Message.fromJson(jsonDecode(data)));
    }

    add(ReceiveMessageEvent());
  }

  Future<Message?> _sendFile({required String? path}) async {
    if (path == null || path.trim().isEmpty) return null;

    final fileFromMemory = File(path);

    final isExist = await fileFromMemory.exists();

    Uint8List? fileToUnit8;

    if (isExist) {
      fileToUnit8 = await fileFromMemory.readAsBytes();
    } else {
      final fileLocal = await rootBundle.load(path);
      fileToUnit8 = fileLocal.buffer.asUint8List();
    }

    final message = Message(
        text: path,
        type: path.getType(),
        size: fileToUnit8.length,
        data: fileToUnit8,
        person: PersonPreference.getInstance());

    return _send(message: message);
    //socket.postObject(address: '/message', value: fileToUint8);
  }

  Message? _sendText() {
    if (chatController.text.isEmpty) return null;

    final message = Message(
        text: chatController.text, person: PersonPreference.getInstance());

    chatController.clear();

    return _send(message: message);
  }

  Message _send({required Message message}) {
    String jsonMessage = jsonEncode(message.toJson());
    socket.postText(address: messageChanel, value: jsonMessage);
    return message;
  }
}
