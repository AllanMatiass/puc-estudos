import 'dart:ffi';

import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'IMC',
      theme: ThemeData(
        colorScheme: .fromSeed(seedColor: Colors.teal),
      ),
      home: const MyHomePage(title: 'IMC'),
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
  final TextEditingController _weightController = TextEditingController();
  final TextEditingController _heightController = TextEditingController();

  double? _imcResult;
  String? _imcClassification;

  void _calcImc(double? weight, double? height) {
    if (weight == null || height == null || weight.isNaN || height.isNaN) {
      ScaffoldMessenger.of(context)
          .showSnackBar(
          SnackBar(
              content: Text('Valores Inválidos')
          )
      );
      return;
    }

    setState(() {
      _imcResult = weight / (height * height);
      _classifyImc();
    });
  }

  void _classifyImc(){
    if (_imcResult == null) return;

    if (_imcResult! <= 18.5) {
      _imcClassification = 'Abaixo do peso';
    } else if (_imcResult! <= 24.9){
      _imcClassification = 'Peso normal';
    } else if (_imcResult! <= 29.9){
      _imcClassification = 'Sobrepeso';
    } else {
      _imcClassification = 'Obeso';
    }
  }

  void clear(){
    setState(() {
      _imcResult = null;
      _imcClassification = null;
      _heightController.clear();
      _weightController.clear();
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
            TextField(
              controller: _heightController,
              decoration: InputDecoration(
                labelText: 'Enter your height',
                border: OutlineInputBorder()
              ),
              keyboardType: TextInputType.number,
            ),
            Padding(padding: EdgeInsetsGeometry.all(10)),
            TextField(
              controller: _weightController,
              decoration: InputDecoration(
                  labelText: 'Enter your weight',
                  border: OutlineInputBorder()
              ),
              keyboardType: TextInputType.number,
            ),
            Padding(padding: EdgeInsetsGeometry.all(10)),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton.icon(
                onPressed: () {
                  _calcImc(
                    double.tryParse(_weightController.text),
                    double.tryParse(_heightController.text),
                  );
                },
                icon: const Icon(Icons.calculate),
                label: const Text("Calcular IMC"),
                style: ElevatedButton.styleFrom(
                  padding: const EdgeInsets.symmetric(vertical: 20),
                ),
              ),
            ),
            Text(_imcResult != null ? 'Your IMC is: ${_imcResult?.toStringAsFixed(2)}' : ''),
            Text(_imcClassification != null ? 'Situação: $_imcClassification': ''),
            Padding(padding: EdgeInsetsGeometry.all(10)),

          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: clear,
        tooltip: 'Clear',
        child: const Icon(Icons.clear),
      ),
    );
  }
}
