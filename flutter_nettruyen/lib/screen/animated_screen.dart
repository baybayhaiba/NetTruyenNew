import 'dart:async';

import 'package:flutter/material.dart';

class AnimatedScreen extends StatefulWidget {
  const AnimatedScreen({Key? key}) : super(key: key);

  @override
  State<AnimatedScreen> createState() => _AnimatedScreenState();
}

class _AnimatedScreenState extends State<AnimatedScreen> with TickerProviderStateMixin {

  late Animation _containerRadiusAnimation,
      _containerSizeAnimation,
      _containerColorAnimation;

  late AnimationController _containerAnimationController;


  @override
  void initState() {
    _containerAnimationController = AnimationController(vsync: this);


    // _containerRadiusAnimation = BorderRadiusTween(
    //     begin: BorderRadius.circular(100.0),
    //     end: BorderRadius.circular(0.0))
    //     .animate(CurvedAnimation(
    //     curve: Curves.ease, parent: _containerAnimationController));



    _containerSizeAnimation = Tween(begin: 0.1, end: 0.8).animate(
        CurvedAnimation(

            curve: Curves.ease, parent: _containerAnimationController));

    _containerColorAnimation =
        ColorTween(begin: Colors.white, end: Colors.white.withOpacity(0.3)).animate(
            CurvedAnimation(
                curve: Curves.ease, parent: _containerAnimationController));


    _containerAnimationController.repeat(period: const Duration(milliseconds: 6000));


    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final width = MediaQuery.of(context).size.width;
    final height = MediaQuery.of(context).size.height;
    return Scaffold(
      appBar: AppBar(
        title: const Text('Animated Screen'),
      ),
      body: Container(
        color: Colors.black,
        child: Center(
          child: AnimatedBuilder(
            animation: _containerAnimationController,
            builder: (context, index) {
              return Container(
                width: _containerSizeAnimation.value * width,
                height: _containerSizeAnimation.value * height,
                decoration: BoxDecoration(
                  shape: BoxShape.circle,
                   // borderRadius: _containerRadiusAnimation.value,
                    color: _containerColorAnimation.value),
              );
            },
          ),
        ),
      ),
    );
  }
}
