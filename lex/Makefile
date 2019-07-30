TARGET = read_throw

LIBS = -ll
LEX = flex
SRCS = main.c
OBJS = $(SRCS:.c=.o) lex.yy.o

.PHONY: all clean

all: $(TARGET)
clean:
	rm -f $(TARGET) $(OBJS) lex.yy.h lex.yy.c
$(TARGET): $(OBJS)
	$(CC) -o $@ $(OBJS) $(LIBS)
main.c: lex.yy.h
lex.yy.h lex.yy.c: lex.l
	$(LEX) --header-file=lex.yy.h $<
