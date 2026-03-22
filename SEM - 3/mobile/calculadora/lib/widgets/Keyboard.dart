import 'package:flutter/material.dart';

class Keyboard extends StatelessWidget {
  final Function(String) onKeyPressed;

  const Keyboard({super.key, required this.onKeyPressed});

  @override
  Widget build(BuildContext context) {
    final List<String> buttons = [
      'C', '⌫', '/', '',
      '7', '8', '9', 'x',
      '4', '5', '6', '-',
      '1', '2', '3', '+',
      '0', '.', '=', ''
    ];

    return GridView.builder(
      padding: const EdgeInsets.all(10),
      itemCount: buttons.length,
      gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 4,
        crossAxisSpacing: 20,
        mainAxisSpacing: 40,
      ),
      itemBuilder: (context, index) {
        final value = buttons[index];

        return ElevatedButton(
          onPressed: () {
            onKeyPressed(value);
          },
          style: ElevatedButton.styleFrom(
            padding: const EdgeInsets.all(20),
          ),
          child: Text(
            value,
            style: const TextStyle(fontSize: 35),
          ),
        );
      },
    );
  }
}