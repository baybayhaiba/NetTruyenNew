import 'dart:typed_data';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/model/message.dart';

class PhotoChat extends StatelessWidget {
  const PhotoChat({Key? key,this.message}) : super(key: key);

  final Message? message;

  @override
  Widget build(BuildContext context) {
    if(message == null) return const SizedBox();
    return Image.memory(_getImageBinary(message?.data),width: 150,height: 150,fit: BoxFit.cover,);
  }


  Uint8List _getImageBinary(collectionBinaryList) {
    List<int> binaryList = collectionBinaryList.cast<int>().toList(); //This is the magical line.
    Uint8List data = Uint8List.fromList(binaryList);
    return data;
  }
}
