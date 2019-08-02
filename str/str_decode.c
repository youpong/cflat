#include <string.h>
#include <stdlib.h>
#include <ctype.h>

char *str_decode(char *literal) {
  char *p, *q;
  char *dst;
  
  dst = (char *)malloc(sizeof(char) * (strlen(literal) + 1));
  p = literal;
  q = dst;
  while(*p != '\0') {
    if( *p != '\\' ) {
      *q++ = *p++;
      continue;
    }
    
    /* 以降は '\' でつづく文字列を処理する */
    p++;
    
    /* \127 */    
    if ('0' <= *(p + 0) && '7' >= *(p + 0) &&
	'0' <= *(p + 1) && '7' >= *(p + 1) &&
	'0' <= *(p + 2) && '7' >= *(p + 2) ) {
      *q = 0;
      for(int i = 0; i < 3; i++) {
	*q = *q * 8 + *(p + i) - '0';
      }
      q++;
      p += 3;
      continue;
    }
    
    /* \, " - literally 
       n, t - trans  
       others -  
     */
    switch(*p) {
    case '\\':
      *q++ = '\\';
      p++;
      break;
    case '"':
      *q++ = '\"';
      p++;
      break;
    case 'n':
      *q++ = '\n';
      p++;
      break;
    case 't':
      *q++ = '\t';
      p++;
      break;
    default:
      *q++ = *p++;
    }
  }
  
  *q = '\0';
  return dst;
}
