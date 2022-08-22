import 'caro_bloc.dart';

extension CaroBlocAlgorithms on CaroBloc {
  int checkCrossRightBotLeft(int row, int column, String character) {
    //[5 : 3] => [6 : 2] => [7:1] => [8 : 0]
    int columnIncrease = column;
    int rowIncrease = row;

    //[5 : 3] => [4 : 4] => [3 : 5] => [2 : 6]
    int columnDecrease = column;
    int rowDecrease = row;

    int countTotal = 1;

    while (++columnIncrease < matrixRectangle.length &&
        --rowDecrease >= 0 &&
        matrixRectangle[columnIncrease][rowDecrease].characterObserver.value ==
            character) {
      countTotal++;
    }

    while (--columnDecrease >= 0 &&
        ++rowIncrease < matrixRectangle[columnDecrease].length &&
        matrixRectangle[columnDecrease][rowIncrease].characterObserver.value ==
            character) {
      countTotal++;
    }

    return countTotal;
  }

  int checkCrossLeftBotRight(int row, int column, String character) {
    //[5 : 3] => [6 : 4] => [7:5]
    int columnIncrease = column;
    int rowIncrease = row;

    //[5 : 3] => [4 : 2] => [3 : 1] => [2 : 0]
    int columnDecrease = column;
    int rowDecrease = row;

    int countTotal = 1;

    while (++columnIncrease < matrixRectangle.length &&
        ++rowIncrease < matrixRectangle[columnIncrease].length &&
        matrixRectangle[columnIncrease][rowIncrease].characterObserver.value ==
            character) {
      countTotal++;
    }

    while (--columnDecrease >= 0 &&
        --rowDecrease >= 0 &&
        matrixRectangle[columnDecrease][rowDecrease].characterObserver.value ==
            character) {
      countTotal++;
    }

    return countTotal;
  }

  int checkColumn(int row, int column, String character) {
    int columnIncrease = column;
    int columnDecrease = column;
    //row = 1 => character row current
    int countColumn = 1;

    while (++columnIncrease < matrixRectangle.length &&
        matrixRectangle[columnIncrease][row].characterObserver.value ==
            character) {
      countColumn++;
    }
    while (--columnDecrease >= 0 &&
        matrixRectangle[columnDecrease][row].characterObserver.value ==
            character) {
      countColumn++;
    }

    return countColumn;
  }

  int checkRow(int row, int column, String character) {
    int rowIncrease = row;
    int rowDecrease = row;
    //row = 1 => character row current
    int countRow = 1;

    while (++rowIncrease < matrixRectangle[column].length &&
        matrixRectangle[column][rowIncrease].characterObserver.value ==
            character) {
      countRow++;
    }
    while (--rowDecrease >= 0 &&
        matrixRectangle[column][rowDecrease].characterObserver.value ==
            character) {
      countRow++;
    }
    return countRow;
  }

  bool isWin(
      {required int column, required int row, required String character}) {
    int countRow = checkRow(row, column, character);
    int countColumn = checkColumn(row, column, character);
    int countCrossLeftBotRight = checkCrossLeftBotRight(row, column, character);
    int countCrossRightBotLeft = checkCrossRightBotLeft(row, column, character);

    if (countRow >= 5 ||
        countColumn >= 5 ||
        countCrossLeftBotRight >= 5 ||
        countCrossRightBotLeft >= 5) return true;

    return false;
  }

}