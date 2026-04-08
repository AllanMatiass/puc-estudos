import 'package:flutter/material.dart';
import 'package:soccer/add_team.dart';
import 'package:soccer/soccer_team.dart';

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
        colorScheme: .fromSeed(seedColor: Colors.green),
      ),
      home: const MyHomePage(title: 'Soccer List'),
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

  final List<SoccerTeam> _teams = [
    SoccerTeam(name: 'Corinthians', foundationYear: 1910)
  ];

  int? _removedIndex;
  SoccerTeam? _lastRemoved;

  void removeTeam(int idx){
    setState(() {
      _lastRemoved = _teams[idx];
      _teams.removeAt(idx);
      _removedIndex = idx;
    });

    final messenger = ScaffoldMessenger.of(context);

    messenger.clearSnackBars();

    messenger.showSnackBar(
      SnackBar(
        content: Text("${_lastRemoved?.name} removed"),
        duration: const Duration(seconds: 10),
        action: SnackBarAction(
          label: 'Desfazer',
          onPressed: () {
            setState(() {
              _teams.insert(_removedIndex!, _lastRemoved!);
            });
          },
        ),
      ),
    );
  }

  Future<void> _navigateToAddTeam() async {
    final SoccerTeam? newTeam = await Navigator.push<SoccerTeam>(
      context,
      MaterialPageRoute(builder: (context) => const AddTeam()),
    );

    if (newTeam != null) {
      setState(() {
        _teams.add(newTeam);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: _teams.isEmpty ? Center(
        child: Text("Nenhum time adicionado, clique no botão para começar"),
      ) :  ListView.builder(itemCount: _teams.length, itemBuilder: (BuildContext ctx, int idx) {
        return Dismissible(
          key: ValueKey("${_teams[idx].name}-${_teams[idx].foundationYear}-$idx"),
          direction: DismissDirection.startToEnd,
          background: Container(
            color: Colors.red,
            child: Center(
              child: Icon(Icons.archive, color: Colors.white,),
            ),),
          onDismissed: (direction){
            removeTeam(idx);
          },
          child: InkWell(
            splashColor: Colors.green.withAlpha(30),
            onTap: () {
              debugPrint('Card tapped.');
            },
            child: SizedBox(
              height: 100,
              child: Center(
                child: ListTile(
                  leading: const Icon(Icons.sports_soccer),
                  title: Text(
                      _teams[idx].name,
                      style: const TextStyle(fontWeight: FontWeight.bold)
                  ),
                  subtitle: Text("${_teams[idx].foundationYear}"),
                ),
              )
            ),
          ),
        );
      }),
      floatingActionButton: FloatingActionButton(
        onPressed: (){
          _navigateToAddTeam();
        },
        child: Icon(Icons.add)
      ),
    );
  }
}
