#include <stdio.h>

void xor(void) {
  int val = 0xC;         // 0xC = 1100(2)

  val ^= 0xA;            // 0xA = 1010(2)

  printf("0x%X\n", val); // 0110(2) = 0x6

  val ^= val;
  
  printf("0x%X\n", val); // 0000(2) = 0x0
}

void not(void) {
  int val = 0x1;         // 1(2)

  val = ~val;

  printf("0x%X\n", val); // 0xFFFFFFFE
}

int main(void) {
  xor();
  not();
  
  return 0;
}
// 32 bit
// 16 bit + 16 bit
// 4bit 1111 => 8+4+2+1 => 15
//FFFFFFFF
