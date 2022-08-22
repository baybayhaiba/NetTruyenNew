import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/bloc/caro/caro_bloc.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class CaroScreen extends StatefulWidget {
  const CaroScreen({Key? key}) : super(key: key);

  @override
  State<CaroScreen> createState() => _CaroScreenState();
}

class _CaroScreenState extends State<CaroScreen> {
  final blocCaro = CaroBloc();

  @override
  Widget build(BuildContext context) {
    final height = MediaQuery.of(context).size.height -
        AppBar().preferredSize.height -
        MediaQuery.of(context).viewPadding.bottom;
    final width = MediaQuery.of(context).size.width;

    return MaterialApp(
      home: Scaffold(
        body: BlocProvider(
          create: (context) => blocCaro..add(CaroEvInitialize(size: Size(width, height))),
          child: BlocBuilder<CaroBloc, CaroState>(
            builder: (context, state) {
              if (state is CaroStLoading) {
                return const Center(
                  child: CircularProgressIndicator(),
                );
              } else if (state is CaroStInitialized) {
                return Center(
                    child: Wrap(children: [
                  for (int i = 0; i < blocCaro.matrixRectangle.length; i++)
                    for (int j = 0; j < blocCaro.matrixRectangle[i].length; j++)
                      _item(column: i, row: j)
                ]));
              } else if (state is CaroStWinning) {
                return Container(
                  color: Colors.green,
                );
              } else if (state is CaroStLosing) {
                return Container(
                  color: Colors.red,
                );
              }

              return Container(
                color: Colors.blue,
              );
            },
          ),
        ),
      ),
    );

    //matrixRectangle.clear();
  }

  Widget _item({required int column, required int row}) {

    return GestureDetector(
      onTap: () => blocCaro.onClick(column: column, row: row),
      child: blocCaro.matrixRectangle[column][row],
    );
  }
}
