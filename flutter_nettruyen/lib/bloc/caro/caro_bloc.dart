import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_nettruyen/widget/rectangle.dart';
import 'package:web_socket_channel/web_socket_channel.dart';
import '../chat/chat_bloc.dart';
import 'caro_algorithms.dart';
import '../../model/player.dart';

abstract class CaroEvent {}

class CaroEvInitialize extends CaroEvent {
  Size size;

  CaroEvInitialize({required this.size});
}

class CaroEvChecking extends CaroEvent {
  int indexColumn;
  int indexRow;
  Player playerPresent;

  CaroEvChecking(
      {required this.indexColumn,
      required this.indexRow,
      required this.playerPresent});
}

abstract class CaroState {}

class CaroStLoading extends CaroState {}

class CaroStInitialized extends CaroState {}

class CaroStWinning extends CaroState {}

class CaroStLosing extends CaroState {}

class CaroBloc extends Bloc<CaroEvent, CaroState> {
  final List<List<Rectangle>> matrixRectangle = [[]];
  late Size size;

  bool isCanClick = true;

  Player get playerOne => Player(index: 1, char: '♥', isCanClick: isCanClick);

  Player get playerTwo => Player(index: 2, char: '☻', isCanClick: !isCanClick);

  CaroBloc() : super(CaroStLoading()) {
    on<CaroEvInitialize>((event, emit) async {
      await Future.delayed(const Duration(milliseconds: 1000));

      size = event.size;

      for (double i = 0; i < 16; i++) {
        matrixRectangle.add([]);
        for (double j = 0; j < size.width; j += size.width / 8) {
          matrixRectangle[i.toInt()].add(Rectangle(
            size: Size(size.width / 8, size.height / 16),
            index: '${i.toInt()} : ${matrixRectangle[i.toInt()].length}',
          ));
        }
      }

      matrixRectangle.removeWhere((element) => element.isEmpty);

      emit(CaroStInitialized());
    });

    on<CaroEvChecking>((event, emit) {
      if (isWin(
          column: event.indexColumn,
          row: event.indexRow,
          character: event.playerPresent.char)) {
        emit(event.playerPresent == playerOne
            ? CaroStWinning()
            : CaroStLosing());
      }
    });
  }

  void onClick({required int column, required int row}) {
    if (matrixRectangle[column][row].characterObserver.value.isNotEmpty) return;

    if (isCanClick) {
      matrixRectangle[column][row].onClick(playerOne.char);
    } else {
      matrixRectangle[column][row].onClick(playerTwo.char);
    }

    add(CaroEvChecking(
        indexColumn: column,
        indexRow: row,
        playerPresent: isCanClick ? playerOne : playerTwo));

    isCanClick = !isCanClick;
  }
}
