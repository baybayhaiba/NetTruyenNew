import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/model/book_model/book_model.dart';

class BookWidget extends StatelessWidget {
  const BookWidget({Key? key, required this.book}) : super(key: key);

  final BookModel book;

  @override
  Widget build(BuildContext context) {
    return Container();
  }

  Widget createBook() => Image.network(book.image);
}
