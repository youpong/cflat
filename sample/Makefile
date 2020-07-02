CC = ./cbc
AS = as
LINKER = cc

# どちらもイケる
#.SUFFIXES: .s .cb
#.SUFFIXES: .cb .s

.SUFFIXES: .cb

.PHONY: clean

all: foo gvar inc memb

clean:
	rm -rf *.s *.o foo gvar inc memb

.cb.s:
	$(CC) -S $<
.s.o:
	$(AS) --32 -o $@ $<
.o:
	$(LINKER) -m32 -o $@ $<
