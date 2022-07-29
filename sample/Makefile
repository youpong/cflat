CC = ./cbc
AS = as
LINKER = cc

# どちらもイケる
#.SUFFIXES: .s .cb
#.SUFFIXES: .cb .s

.SUFFIXES: .cb

.PHONY: clean

all: 42 func gvar memb if if-else lvar func_ptr

clean:
	rm -rf *.s *.o a.out \
		42 func gvar memb if if-else lvar func_ptr

.cb.s:
	$(CC) -S $<
.s.o:
	$(AS) --32 -o $@ $<
.o:
	$(LINKER) -m32 -o $@ $<
