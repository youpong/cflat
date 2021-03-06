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

  expect(__LINE__, TOKEN_CHAR, yylex());
  expect_str(__LINE__, "a", str_decode(yytext));

  expect(__LINE__, TOKEN_CHAR, yylex());
  expect_str(__LINE__, "W", str_decode(yytext));
}
/*
errors
/*<<EOF>> - missing terminating
''        - empty character
'ab'      - multi-character
"\n       - missing terminating
"<<EOF>>  - missing terminating
'\n       - missing terminating
'<<EOF>>  - missing terminating
*/

