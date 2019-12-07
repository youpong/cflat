# 14.2 のサンプル assembly code を assemble する。
#
# F.asm:        x86 intel_syntax 
# F_att.asm:    x86 att_syntax
#
# F64.asm:      x86-64 att_syntax f()
# F64_main.asm: x86-64 att_syntax main()
#
# TODO f.c:          c
# test_f.c:     test driver for assembly code

AS = nasm
CFLAGS = -g

all: f F F_att F64
clean:
	rm -f a.out *.o \
	      f F F_att F64 test_f

# F
F: F.o
F.o: F.asm
	nasm -f elf32 -g -F dwarf F.asm -l F.lst
# F_att
F_att: F_att.o
F_att.o: F_att.asm
	nasm -f elf32 -g -F dwarf F_att.asm -l F_att.lst

# F64
F64: F64.o F64_main.o
F64.o: F64.asm
	$(AS) -f elf64 -g -F dwarf $<
F64_main.o: F64_main.asm
	$(AS) -f elf64 -g -F dwarf $<

# f
f: f.o

# test_f
test_f: test_f.o F64.o
