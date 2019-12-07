# 14.2 のサンプル assembly code を assemble する。
#
# f32_gas.asm:  gas  x86
# f32.asm:      nasm x86    f()
# f32_main.asm: nasm x86    main()
# f64.asm:      nasm x86-64 f()
# f64_main.asm: nasm x86-64 main()
#
# f.c:          original c source code
# test_f.c:     test driver for assembly code

AS = nasm
CFLAGS = -g

.PHONY: all clean

all: f32_gas f32 f64
clean:
	rm -f a.out *.o *.lst test_f32 test_f64 f f32_gas f32 f64

# f32_gas
f32_gas: f32_gas.o
	$(CC) -m32 -o $@ $^
f32_gas.o: f32_gas.asm
	as -g --32 -o $@ $^

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

# test_f64
test_f64: test_f64.o f64.o
	$(CC) -o $@ $^
test_f64.o: test_f.c
	$(CC) -c -o $@ $^

# test_f32
test_f32: test_f32.o f32.o
	$(CC) -m32 -o $@ $^
test_f32.o: test_f.c
	$(CC) -m32 -c -o $@ $^
