import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_nettruyen/widget/chat/unity/unities.dart';

class IconChat extends StatelessWidget {
  IconChat({Key? key, this.onTap}) : super(key: key);

  final List<String> icons = [
    '🙂',
    '😣',
    '😩',
    '😀',
    '😄',
    '😫',
    '😕',
    '😆',
    '😅',
    '🤔',
    '🙄',
    '😂',
    '🤣',
    '😤',
    '😠',
    '😊',
    '☺',
    '️😡',
    '😶',
    '😌',
    '😑',
    '😯',
    '😘',
    '😗',
    '😲',
    '😧',
    '😙',
    '😚',
    '😨',
    '😰',
    '🤗',
    '😳',
    '😱',
    '😪',
    '🙃',
    '😇',
    '😴',
    '😬',
    '😈',
    '😛',
    '🤥',
    '🐵',
    '🙈',
    '🙉',
    '🙊',
    '🦍',
    '🐺',
    '🐸',
    '🐽',
    '🐷',
    '🐗',
    '🇻🇳',
    '⚙',
    '🇧🇧',
    '💊',
    '🔴',
    '⬜',
  ];

  final Function(TypeIcon, String)? onTap;

  @override
  Widget build(BuildContext context) {
    final width = kIsWeb ? 50.0 : (MediaQuery.of(context).size.width / 6) - 32;

    return Column(
      children: [
        Wrap(
            spacing: 12,
            runSpacing: 12,
            children: icons
                .map((value) => Material(
                      child: InkWell(
                        onTap: () => onTap?.call(TypeIcon.icon, value),
                        child: Text(
                          value,
                          style: TextStyle(fontSize: width),
                        ),
                        borderRadius:
                            const BorderRadius.all(Radius.circular(8)),
                        splashColor: Colors.grey,
                      ),
                    ))
                .toList()),
      ],
    );
  }
}
