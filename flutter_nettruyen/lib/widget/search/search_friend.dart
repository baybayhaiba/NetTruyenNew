import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/constants/layout_constants.dart';

class SearchFriend extends StatelessWidget {
  const SearchFriend({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          color: secondary, borderRadius: BorderRadius.circular(10)),
      height: LayoutConstants.containerHeight,
      child: ClipRRect(
        child: Padding(
          padding: const EdgeInsets.only(left: 15),
          child: TextField(
            style: const TextStyle(color: Colors.white),
            decoration: InputDecoration(
                hintText: 'Search my friend',
                hintStyle: const TextStyle(color: Colors.white54),
                border: InputBorder.none,
                suffixIcon: Container(
                  height: 40,
                  width: 40,
                  decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(10),
                      color: const Color(0xff565E70)),
                  child: const Icon(
                    Icons.search_rounded,
                    color: Colors.white,
                  ),
                )),
          ),
        ),
      ),
    );
  }
}
