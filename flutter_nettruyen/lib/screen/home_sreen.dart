import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/main.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:flutter_nettruyen/widget/search/search_friend.dart';
import 'package:flutter_nettruyen/widget/user/user_activity.dart';
import '../constants/layout_constants.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);
  static const String router = 'home_screen';

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int selectIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: primary,
      body: SingleChildScrollView(
        child: WillPopScope(
          onWillPop: () {
            callToNative(command: CommandType.exit);
            return Future.value(false);
          },
          child: Padding(
            padding: EdgeInsets.only(
                top: 16 + MediaQuery.of(context).viewPadding.top,
                left: 16,
                right: 16),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Container(
                  decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(16),
                      color: Colors.white),
                  child: IconButton(
                      icon: const Icon(Icons.arrow_back_ios_new),
                      onPressed: () => callToNative(command: CommandType.exit)),
                ),
                const SizedBox(height: 36),
                Padding(
                  padding: const EdgeInsets.only(bottom: 10),
                  child: Row(
                    children: [
                      ClipRRect(
                        borderRadius: BorderRadius.circular(60 / 2),
                        child: const Image(
                          image: AssetImage('assets/image/chibi_one.png'),
                          width: 45,
                          height: 45,
                        ),
                      ),
                      const SizedBox(width: 10),
                      Text(
                        'ChaTdc1z',
                        style: GoogleFonts.lato(
                            fontSize: 27,
                            color: Colors.white,
                            fontWeight: FontWeight.bold),
                      ),
                    ],
                  ),
                ),
                Row(
                  children: [
                    const Expanded(
                      child: SearchFriend(),
                    ),
                    const SizedBox(width: 16),
                    _buttonAction(path: 'assets/icon/ic_setting.svg'),
                  ],
                ),
                ListView.separated(
                    padding: const EdgeInsets.only(top: 24),
                    itemCount: 50,
                    primary: false,
                    physics: const NeverScrollableScrollPhysics(),
                    shrinkWrap: true,
                    separatorBuilder: (context, index) =>
                        const SizedBox(height: 24),
                    itemBuilder: (context, index) => const UserActivity()),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Material _buttonAction({required String path}) {
    return Material(
      color: Colors.transparent,
      child: InkWell(
          borderRadius: const BorderRadius.all(Radius.circular(32)),
          onTap: () {},
          child: Container(
              padding: const EdgeInsets.all(16),
              child: SvgPicture.asset(
                path,
                height: 20,
                width: 20,
              ))),
    );
  }

  Widget createContent({required BuildContext context}) => Container(
        color: Colors.blue,
      );
}
