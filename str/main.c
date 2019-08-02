#include "lex.yy.h"
#include "main.h"

int main() {
  int ret;
  while ((ret = yylex()) != 0) {
    if (ret == TOKEN_STRING)
      printf("TOKEN_STRING(%s:%s)", yytext, str_decode(yytext));
  }
  
  return 0;
}
  
