%{

// returns tokens.
// - string literal(TOKEN_STRING) "string"
// - character literal (TOKEN_CHAR) 'c'
// throw away
// - block comment /* comment */
// - line comment  // comment

#include "main.h"
#include "util.h"
%}
%option nounput noinput
%x IN_STRING IN_CHAR TERM_CHAR IN_BLOCK_COMMENT

%%
 /*
  * line commnet
  */
"//".*                    { /* throw away */; }

 /*
  * block comment
  */
"/*"                      { BEGIN IN_BLOCK_COMMENT; }
<IN_BLOCK_COMMENT>.       { /* throw away */; }
<IN_BLOCK_COMMENT><<EOF>> { error("unterminated /* comment"); }
<IN_BLOCK_COMMENT>"*/"    { BEGIN 0; /* throw away */ }

 /*
  * string literal
  */
\"                  { BEGIN IN_STRING; }
<IN_STRING>[^\\\n"] |
<IN_STRING>\\.      { yymore(); }
<IN_STRING><<EOF>>  |
<IN_STRING>\n       { error("missing terminating '\"' character"); }
<IN_STRING>\"       { 
                      BEGIN 0; 
                      yytext[yyleng-1] = '\0';
                      return TOKEN_STRING;
                    }

 /* 
  * character literal 
  */
\'                  { BEGIN IN_CHAR; }
<IN_CHAR>[^\\\n']   |
<IN_CHAR>\\[0-7]{3} |
<IN_CHAR>\\.        { yymore(); BEGIN TERM_CHAR; }
<IN_CHAR><<EOF>>    |
<IN_CHAR>\n         { error("missing terminating '\\'' character"); }
<IN_CHAR>\'         { error("empty character literal"); }
<TERM_CHAR>[^']     { error("multi-character character literal"); }
<TERM_CHAR>\'       {
                      BEGIN 0;
                      yytext[yyleng - 1] = '\0';
                      return TOKEN_CHAR;
                    }

 /* 
  * Gleaning 
  */
.|\n                { /* ignore */; }
%%

