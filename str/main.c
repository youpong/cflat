#include "lex.yy.h"
#include "main.h"

int main(int argc, char **argv) {
  int ret;

  if(argc == 2 && !strcmp(argv[1], "--test")) {
    test_str_decode();
    test_lex();
    return 0;
  }

  while ((ret = yylex()) != 0) {
    if (ret == TOKEN_STRING)
      printf("TOKEN_STRING(%s:%s)", yytext, str_decode(yytext));
  }

  return 0;
}

