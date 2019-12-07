# 14.2 のサンプル assembly code を assemble する。
#
# F.asm:        x86 intel_syntax 
# F_att.asm:    x86 att_syntax
#
# F64.asm:      x86-64 att_syntax f()
# F64_main.asm: x86-64 att_syntax main()
#
# f.c:          c
# test_f.c:     test driver for assembly code, f()

AS = nasm
CFLAGS = -g

all: f F F_intel F64
clean:
	rm -f a.out *.o *.lst f F F_intel F64 test_f

# F
F: F.o
F.o: F.asm
	nasm -f elf32 -g -F dwarf F.asm -l F.lst

# F_intel
F_intel: F_intel.o
	$(CC) -m32 -o $@ $< 
F_intel.o: F_intel.asm
	nasm -f elf32 -g -F dwarf F_intel.asm -l F_intel.lst

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
