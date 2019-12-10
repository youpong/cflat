# bit_operator.c:    bit operator sample
# comment_gas.asm:   gas comment sample
# comment_nasm.asm:  nasm comment sample

LDFLAGS = -g

.PHONY: all clean

all: bit_operator comment_gas comment_nasm
clean:
	rm -f a.out *.o bit_operator comment_gas comment_nasm

# comment_gas
comment_gas.o: comment_gas.asm
	$(AS) -g -o $@ $^

# comment_nasm
comment_nasm.o: comment_nasm.asm
	nasm -f elf64 -g -F dwarf $^
