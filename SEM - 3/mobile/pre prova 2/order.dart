void order(List<int> l) {
  bool swap = false;

  do {
    swap = false;
    for (int i = 0; i < l.length - 1; i++) {
      if (l[i] > l[i + 1]) {
        swap = true;
        int temp = l[i];
        l[i] = l[i + 1];
        l[i + 1] = temp;
        break;
      }
    }
  } while (swap);
}

void main() {
  List<int> l = [4, 3, 2, 6, 1, 7, 8, 9];
  order(l);
  print(l);
}
