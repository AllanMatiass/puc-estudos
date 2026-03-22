import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: .fromSeed(seedColor: Colors.teal),
      ),
      home: const MyHomePage(title: 'IMC legal'),
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
  final pesoController = TextEditingController();
  final alturaController = TextEditingController();
  double? res;
  String? classificacao;

  void _calcImc(final double? peso, final double? altura){
    if (peso == null || altura == null || peso.isNaN || altura.isNaN){
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Dá nao pai')));
      return;
    }


    setState(() {


      res = peso / (altura * altura);
      classificacao = _classificar(res);
    });

  }

  String? _classificar(final double? res){
    if (res == null){
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Sem resultado seu tchola')));
      return null;
    }

    if (res < 18.5) return 'palito';

    if (res < 24.9) return 'normal';

    if (res < 29.9) return 'gordin';

    if (res < 34.5) return 'gordão';
;
    if (res < 39.9) return 'imenso';

    return 'baleia';
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
              controller: pesoController,
              keyboardType: TextInputType.number,
              decoration: InputDecoration(
                  border: OutlineInputBorder(),
                  label: Text('Peso')
              ),
            ),
            Padding(padding: EdgeInsets.all(10)),
            TextField(
                controller: alturaController,
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                  label: Text('Altura')
                )
            ),
            Padding(padding: EdgeInsets.all(10)),
            FilledButton(
                onPressed: (){
                  _calcImc(double.tryParse(pesoController.text), double.tryParse(alturaController.text));
                  },
                child: Icon(Icons.calculate_sharp)),
                
            Padding(padding: EdgeInsets.all(10)),
            Text(res != null ? 'Seu IMC é: ${res!.toStringAsFixed(2)}' : 'Tu nao existe'),
            Text(classificacao != null ? classificacao! : 'desclassificado'),
          ],
        ),
      ),
    );
  }
}
