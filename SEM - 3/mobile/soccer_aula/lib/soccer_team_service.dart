import 'package:cloud_functions/cloud_functions.dart';

class SoccerTeamService {
  final FirebaseFunctions _functions = FirebaseFunctions.instance;

  Future<void> addNewSoccerTeam({
    required String name,
    required int foundationYear,
  }) async {
    final callable = _functions.httpsCallable('addNewSoccerTeam');
    await callable.call({
      'name': name,
      'foundationYear': foundationYear,
    });
  }

  Future<List<Map<String, dynamic>>> getSoccerTeams() async {
    final callable = _functions.httpsCallable('getSoccerTeams');
    final result = await callable.call();
    final data = result.data as Map<String, dynamic>;
    final teams = List<Map<String, dynamic>>.from(data['teams']);
    return teams;
  }
}