import 'package:flutter/material.dart';
import 'package:soccer/soccer_team.dart';

class AddTeam extends StatefulWidget {
  const AddTeam({super.key});



  @override
  State<StatefulWidget> createState() => AddTeamState();
}

class AddTeamState extends State<AddTeam> {
  TextEditingController name = TextEditingController();
  TextEditingController foundationYear = TextEditingController();

  SoccerTeam? createSoccerTeam(){
    int? year = int.tryParse(foundationYear.text);
    if (year == null) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text("Ano inválido")));
      return null;
    }
    return SoccerTeam(name: name.text, foundationYear: year);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Adicionar time")),
      body: Center(
        child: Column(
          children: [
            TextField(
              controller: name,
              decoration: InputDecoration(
                  border: OutlineInputBorder(),
                  labelText: "Nome do time",
                  hintText: "Corinthians"
              ),
            ),
            Padding(padding: EdgeInsets.all(16)),
            TextField(
              controller: foundationYear,
              decoration: InputDecoration(border: OutlineInputBorder(),
                  labelText: "Ano de fundação do time",
                  hintText: "1910"
              ),
              keyboardType: TextInputType.number,

            ),
            Padding(padding: EdgeInsets.all(16)),
            ElevatedButton(onPressed: (){
              Navigator.pop(context, createSoccerTeam());
            }, child: Center(child: Icon(Icons.add),))
          ],
        ),
      ),
    );
  }

}