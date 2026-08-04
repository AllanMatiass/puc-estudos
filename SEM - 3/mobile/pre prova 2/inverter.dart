List<int> l = [1, 2, 3, 4, 5, 6];

void inverse(List<int> l) {
  int last = l.length - 1;
  for (int i = 0; i < l.length; i++) {
    int aux = l[i];
    l[i] = last;
    l[last] = aux;
    last -= 1;
  }
}

void main() {
  inverse(l);
  print(l);
}
