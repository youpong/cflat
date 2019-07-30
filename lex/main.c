#include "lex.yy.h"
#include "main.h"

/* comment */
int main() {
  int ret;
  while ((ret = yylex()) != 0) {
    /* no-op */;
    if (ret == TOKEN_STRING_LITERAL)
      printf("TOKEN_STRING_LITERAL(%s)", yytext);
  }
  
  return 0;
}
  
