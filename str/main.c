#include "lex.yy.h"
#include "main.h"

int main(int argc, char **argv) {
  int ret;

  if(argc == 2 && !strcmp(argv[1], "--test")) {
    test_str_decode();
    test_lex();
    printf("===================\n"
	   " All tests passed. \n"
	   "===================\n");
    return 0;
  }

  while ((ret = yylex()) != 0) {
    switch(ret) {
    case TOKEN_STRING:
      printf("TOKEN_STRING(%s:%s)\n", yytext, str_decode(yytext));
      break;
    case TOKEN_CHAR:
      printf("TOKEN_CHAR(%s:%s)\n", yytext, str_decode(yytext));
      break;
    }
  }

  return 0;
}

