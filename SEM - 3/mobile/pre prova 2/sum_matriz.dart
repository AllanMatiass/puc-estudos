List<List<int>> m = [
  [3, 3, 3],
  [3, 3, 3],
];

int sumMatrix(List<List<int>> m) {
  int s = 0;
  for (int i = 0; i < m.length; i++) {
    for (int j = 0; j < m[i].length; j++) {
      s += m[i][j];
    }
  }
  return s;
}

void main() {
  print(sumMatrix(m));
}
