# 14.2 のサンプル assembly code を assemble する。
#
# f32_att.asm:  x86 att_syntax
# f32.asm:      x86    f()
# f32_main.asm: x86    main()
# f64.asm:      x86-64 f()
# f64_main.asm: x86-64 main()
#
# f.c:          original c source code
# test_f.c:     test driver for assembly code, f()

AS = nasm
CFLAGS = -g

.PHONY: all clean

all: f32_att f32 f64
clean:
	rm -f a.out *.o *.lst test_f f f32_att f32 f64

# f32_att
f32_att: f32_att.o
	$(CC) -m32 -o $@ $^
f32_att.o: f32_att.asm
	$(AS) -f elf32 -g -F dwarf $^ -l $*.lst

# f32
f32: f32.o f32_main.o
	$(CC) -m32 -o $@ $^
f32.o: f32.asm
	$(AS) -f elf32 -g -F dwarf $^ -l $*.lst
f32_main.o: f32_main.asm
	$(AS) -f elf32 -g -F dwarf $^ -l $*.lst

# f64
f64: f64.o f64_main.o
f64.o: f64.asm
	$(AS) -f elf64 -g -F dwarf $^
f64_main.o: f64_main.asm
	$(AS) -f elf64 -g -F dwarf $^

# f
f: f.o

# test_f
test_f: test_f.o f64.o
