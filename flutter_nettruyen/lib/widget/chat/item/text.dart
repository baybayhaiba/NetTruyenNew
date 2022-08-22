import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class ChatText extends StatelessWidget {
  const ChatText({Key? key, this.content = '', this.isSender = true})
      : super(key: key);

  final bool isSender;
  final String content;

  @override
  Widget build(BuildContext context) {
    return Text(
      content,
      style: GoogleFonts.lato(fontSize: 19, color: Colors.white),
    );
  }
}
