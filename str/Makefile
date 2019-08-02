TARGET = tokenizer

LIBS = -ll
LEX = flex
SRCS = main.c str_decode.c util.c test_str_decode.c
OBJS = $(SRCS:.c=.o) lex.yy.o

.PHONY: all clean check

all: $(TARGET)
clean:
	rm -f $(TARGET) $(OBJS) lex.yy.h lex.yy.c
check: $(TARGET)
	./$(TARGET) --test
$(TARGET): $(OBJS)
	$(CC) -o $@ $(OBJS) $(LIBS)
lex.yy.h lex.yy.c: lex.l
	$(LEX) --header-file=lex.yy.h $<

main.c: lex.yy.h main.h
test_str_decode.c: lex.yy.h main.h util.h
lex.yy.c: util.h main.h
util.c: util.h
