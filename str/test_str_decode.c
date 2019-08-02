/*
 * unit test for str_decode()
 */
#include <stdio.h>
#include <string.h>
#include "lex.yy.h"
#include "main.h"
#include "util.h"

static void test_routine(int num, char *src, int de_len, char* de_str) {
  char *dst;
  
  dst = str_decode(src);
  expect(num, de_len, strlen(dst));
  expect_str(num, de_str, dst);
}

void test_str_decode() {
  test_routine(__LINE__, "", 0, "");
  test_routine(__LINE__, "a", 1, "a");
  test_routine(__LINE__, "ab", 2, "ab");
  test_routine(__LINE__, "\\", 1, "\134");
  test_routine(__LINE__, "\\\"", 1, "\042"); 
  test_routine(__LINE__, "\\n", 1, "\012");
  test_routine(__LINE__, "\\t", 1, "\011");
  test_routine(__LINE__, "\\127", 1, "W");
}
