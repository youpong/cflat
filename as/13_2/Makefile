# syntax_gas.asm:   GNU as(gas) syntax sample code
# syntax_nasm.asm:  nasm syntax sample code

LDFLAGS = -g

.PHONY: all clean

all: syntax_gas syntax_nasm
clean:
	rm -f a.out *.o syntax_gas syntax_nasm

syntax_gas.o: syntax_gas.asm
	$(AS) -g -o $@ $^

syntax_nasm.o: syntax_nasm.asm
	nasm -f elf64 -g -F dwarf $^
