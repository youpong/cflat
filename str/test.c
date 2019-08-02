#include <stdio.h>
#include <string.h>
#include "lex.yy.h"
#include "main.h"
#include "util.h"

void test_lex() {
  yyin = fopen("test_data.c", "r");

  expect(__LINE__, TOKEN_STRING, yylex());
  expect_str(__LINE__, "", yytext);

  expect(__LINE__, TOKEN_STRING, yylex());
  expect_str(__LINE__, "\"\t\n\127\\", str_decode(yytext));
}
  


