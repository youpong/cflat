# bit_operator.c:    bit operator sample
# comment_gas.asm:   gas comment sample
# comment_nasm.asm:  nasm comment sample
# Hello.c:           equivalent in c source to assembly hello.s
# hello.s:           13.1 hello.s(assembly which gcc compiles Hello.c)
# hello_cflat.asm:   assembly which cflat compiles hello.cb
# hello64.asm:       gas x86-64 AT&T syntax
# hello64_intel.asm: gas x86-64 intel syntax

LDFLAGS = -g

.PHONY: all clean

all: bit_operator comment_gas comment_nasm hello_cflat hello64 hello64_intel
clean:
	rm -f a.out *.o bit_operator comment_gas comment_nasm \
              hello hello_cflat hello64 hello64_intel

# comment_gas
comment_gas.o: comment_gas.asm
	$(AS) -g -o $@ $^

# comment_nasm
comment_nasm.o: comment_nasm.asm
	nasm -f elf64 -g -F dwarf $^

# hello
hello: hello.o
	$(CC) $(LDFLAGS) -m32 -o $@ $^
hello.o: hello.s
	$(AS) --32 -o $@ $^

# hello_cflat(<- cflat compile hello.cb)
hello_cflat: hello_cflat.o
	$(CC) $(LDFLAGS) -m32 -o $@ $^
hello_cflat.o: hello_cflat.asm
	$(AS) --32 -o $@ $^

# hello64
hello64.o: hello64.asm
	$(AS) -o $@ $^

# hello64_intel
hello64_intel.o: hello64_intel.asm
	$(AS) -o $@ $^
