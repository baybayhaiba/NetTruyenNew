import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/model/book_model/book_model.dart';

class TranslateScreen extends StatelessWidget {
  const TranslateScreen({Key? key, this.books = const []}) : super(key: key);

  final List<BookModel> books;

  @override
  Widget build(BuildContext context) {
    return Container(color: Colors.deepOrange,);
  }

}
