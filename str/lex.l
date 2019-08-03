%{
/*
returns tokens.
- string literal(TOKEN_STRING)
- character literal (TOKEN_CHAR)
throw away
- block comment
*/
#include "main.h"
#include "util.h"
%}
%option nounput noinput
%x IN_STRING IN_CHAR TERM_CHAR IN_BLOCK_COMMENT

%%
"/*"                      { BEGIN IN_BLOCK_COMMENT; }
<IN_BLOCK_COMMENT>.
<IN_BLOCK_COMMENT><<EOF>> { error("unterminated /* comment"); }
<IN_BLOCK_COMMENT>"*/"    { BEGIN 0; }

\"                  { BEGIN IN_STRING; }
<IN_STRING>[^\\\n"] { yymore(); }
<IN_STRING>\\.      { yymore(); }
<IN_STRING>\n       { error("missing terminating '\"' character"); }
<IN_STRING><<EOF>>  { error("missing terminating '\"' character"); }
<IN_STRING>\"       { 
                      BEGIN 0; 
                      yytext[yyleng-1] = '\0';
                      return TOKEN_STRING;
                    }

\'                  { BEGIN IN_CHAR; }
<IN_CHAR>[^\\\n']   { yymore(); BEGIN TERM_CHAR; }
<IN_CHAR>\\[0-7]{3} { yymore(); BEGIN TERM_CHAR; }
<IN_CHAR>\\.        { yymore(); BEGIN TERM_CHAR; }
<IN_CHAR>\n         { error("missing terminating '\\'' character"); }
<IN_CHAR><<EOF>>    { error("missing terminating '\\'' character"); }
<IN_CHAR>\'         { error("empty character literal"); }
<TERM_CHAR>\'       {
                      BEGIN 0;
                      yytext[yyleng - 1] = '\0';
                      return TOKEN_CHAR;
                    }
<TERM_CHAR>[^']     { error("multi-character character literal"); }
.|\n                { /* ignore */; }
%%

