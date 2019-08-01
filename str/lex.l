%{
/*
状態遷移を用いて文字列リテラル(TOKEN_STRING)を返却する。
*/
#include "main.h"
%}
%option nounput noinput
%x IN_STRING

%%
\"                       { BEGIN IN_STRING; }
<IN_STRING>[^\\\n"] { yymore(); }
<IN_STRING>\\([0-7]){3} { 
    yymore();
}
<IN_STRING>\"  { 
    BEGIN 0; 
    yytext[yyleng-1] = '\0';
    return TOKEN_STRING;
}
%%

