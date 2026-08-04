List<List<int>> hanoi(int n, List<int> src, List<int> aux, List<int> target) {
  if (n == 1) {
    target.add(src.removeLast());
    return [src, aux, target];
  }

  hanoi(n - 1, src, target, aux);

  target.add(src.removeLast());

  hanoi(n - 1, aux, src, target);

  return [src, aux, target];
}

void main() {
  print(hanoi(3, [1, 2, 3], [], []));
}
