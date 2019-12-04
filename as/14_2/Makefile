# 14.2 のサンプル assembly code を assemble する。
#
# F.asm:        x86 intel_syntax 
# F_att.asm:    x86 att_syntax
# F64.asm:      x86-64 att_syntax f()
# F64_main.asm: x86-64 att_syntax main()
# f.c:          c
# test_f.c:     test driver for assembly code

clean:
	rm -f a.out *.o \
              F64 f test_f
all: Hello hello hello64 hello64-intel

F: F.o
F_att.o: F_att.asm
	nasm -f elf32 -g -F dwarf F_att.asm -l F_att.lst
