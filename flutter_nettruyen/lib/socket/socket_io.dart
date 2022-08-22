import 'dart:async';
import 'package:flutter_nettruyen/constants/layout_constants.dart';
import 'package:socket_io_client/socket_io_client.dart' as IO;

class SocketIoServer {
  final IO.Socket socket;
  final Map<String, StreamController> listeners = {};

  SocketIoServer({required this.socket});

  void postText({required String address, required String value}) {
    socket.emit(address, value);
  }

  void postObject({required String address, required dynamic value}) {
    socket.emit(address, value);
  }

  StreamController observer({required String address}) {
    if (listeners[address] == null) {
      listeners[address] = StreamController.broadcast();

      socket.on(address, (data) => {listeners[address]?.sink.add(data)});
    }

    return listeners[address]!;
  }

  static SocketIoServer? _instance;

  static void initializeSocket({Function(String)? onConnect}) {
    if (_instance == null) {
      IO.Socket socket =
          IO.io('https://socket.caovanchien.com/', <String, dynamic>{
        'transports': [protocol],
        'autoConnect': false,
      });
      _instance = SocketIoServer(socket: socket);
      _instance?.socket.connect();
    }

    _instance?.socket.on(welcomeChanel, (data) => onConnect?.call(data));
  }

  static SocketIoServer getInstance() => _instance!;
}
