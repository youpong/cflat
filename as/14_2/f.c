int f(int x, int y) {
  int i, j;

  i = x;
  j = i * y;
  return j;
}

int main(void) {
  int i = 77;

  i = f(i, 8);
  i %= 5;
  return i;
}
