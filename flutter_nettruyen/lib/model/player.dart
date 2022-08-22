import 'package:equatable/equatable.dart';
// ignore: must_be_immutable
class Player extends Equatable{
  int index;
  String char;
  bool isCanClick;

  Player({this.index = 1, this.char = '♥', this.isCanClick = true});

  @override
  List<Object?> get props => [index,char];
}