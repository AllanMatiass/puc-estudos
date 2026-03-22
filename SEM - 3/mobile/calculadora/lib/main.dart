import 'package:calculadora/widgets/Keyboard.dart';
import 'package:flutter/material.dart';
import 'package:math_expressions/math_expressions.dart';

void main() {
  runApp(const CalculatorApp());
}

class CalculatorApp extends StatelessWidget {
  const CalculatorApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Calculator',
      theme: ThemeData(
        colorScheme: .fromSeed(seedColor: Colors.lightBlue),
      ),
      home: const MyHomePage(title: 'Calculator'),
      debugShowCheckedModeBanner: false,
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});


  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final TextEditingController _displayController = TextEditingController();


  bool _isOperator(String value){
    return value == 'x' || value == '+' || value == '-' || value == '/' || value == '*';
  }

  void _evaluate(){
    String text = _displayController.text;
    try {
      GrammarParser p = GrammarParser();
      Expression exp = p.parse(text.replaceAll('x', '*'));

      ContextModel cm = ContextModel();

      double result = exp.evaluate(EvaluationType.REAL, cm);

      _displayController.text = result % 1 == 0
          ? result.toInt().toString()
          : result.toString();

    } catch (e) {
      _displayController.text = 'Erro';
    }
  }

  void _erase(String text){
    _displayController.text =
        text.substring(0, text.length - 1);
  }

  void _handleKey(String value) {
    if (value.isEmpty) return;
    setState(() {
      String text = _displayController.text;
      String oneBefore = text.isNotEmpty ? text[text.length - 1] : '';

      if (value == 'C') {
        _displayController.clear();
      } else if (value == '=') {
        _evaluate();
      } else if (value == '⌫' && text.isNotEmpty) {
        _erase(text);

      } else if (_isOperator(value)) {
        if (text.isEmpty) return;

        if (_isOperator(oneBefore)) {
          _erase(text);
        }

        _displayController.text += value;

      } else if (value == '%') {
        if (text.isNotEmpty && double.tryParse(oneBefore) != null) {
          _displayController.text += value;
        }
      } else if (value == '.'){
        if (oneBefore == '.') return;
        _displayController.text += value;
      }
      else {
        if (text == 'Erro') _displayController.clear();
        _displayController.text += value;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),

      ),
      body: Center(
        child: Column(
          mainAxisAlignment: .center,
          children: [
            Padding(padding: EdgeInsets.all(10)),
             TextField(
               readOnly: true,
               controller: _displayController,
               style: TextStyle(fontSize: 25),
               decoration: const InputDecoration(
                 border: OutlineInputBorder(),
                 contentPadding: EdgeInsets.all(20),

               ),
             ),
            Padding(padding: EdgeInsets.all(10)),
            Expanded(
                child: Keyboard(onKeyPressed: _handleKey)
            )
          ],
        ),
      ),
    );
  }
}
