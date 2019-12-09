# comment_gas.asm:  gas comment sample
# comment_nasm.asm: nasm comment sample
# hello_cflat:      assembly which cflat compiles hello.cb
# hello64:          gas x86-64 AT&T syntax
# hello64_intel:    gas x86-64 intel syntax

LDFLAGS = -g

.PHONY: all clean

all: comment_gas comment_nasm hello_cflat hello64 hello64_intel
clean:
	rm -f a.out *.o comment_gas comment_nasm \
              hello_cflat hello64 hello64_intel bit_operator

# comment_gas
comment_gas.o: comment_gas.asm
	as -g -o $@ $^

# comment_nasm
comment_nasm.o: comment_nasm.asm
	nasm -f elf64 -g -F dwarf $^

# hello_cflat(<- cflat compile hello.cb)
hello_cflat: hello_cflat.o
	$(CC) -m32 -o $@ $^
hello_cflat.o: hello_cflat.asm
	$(AS) --32 -o $@ $^

# hello64
hello64: hello64.o
	$(CC) -o $@ $^
hello64.o: hello64.asm
	$(AS) -o $@ $^

# hello64_intel
hello64_intel: hello64_intel.o
	$(CC) -o $@ $^
hello64_intel.o: hello64_intel.asm
	$(AS) -o $@ $^
