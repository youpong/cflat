%{
/*
状態遷移を用いてブロックコメント、文字列リテラル、文字リテラル
の token の切り出しあるいは無視を行うサンプル
BLOCK_COMMENT STRING_LITERAL CHARACTER_LITERAL
*/
#include "main.h"
%}
%option nounput noinput
%x BLOCK_COMMENT STRING_LITERAL

%%
"/*"                { BEGIN BLOCK_COMMENT; /* printf("comment\n"); */}
<BLOCK_COMMENT>"*/" { BEGIN 0; /* printf("end comment\n"); */}
<BLOCK_COMMENT>.    { /* no-op */ ; /* printf("no-op\n"); */}

\"                  { BEGIN STRING_LITERAL; }
<STRING_LITERAL>[^\\\n\r"] { yymore(); }
<STRING_LITERAL>\"  { 
    BEGIN 0; 
    yytext[yyleng-1] = '\0';
    return TOKEN_STRING_LITERAL;
}

%%
/*
.|\n                { ECHO ; }
*/  
