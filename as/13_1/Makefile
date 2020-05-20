CFLAGS = -Os
ASFLAGS = -g

.PHONY: all clean

all: hello hello_cflat hello64 hello64_intel
all_assembly: Hello64_intel.s Hello64_att.s Hello32_intel.s Hello32_att.s

clean:
	rm -f a.out *.o \
              hello hello_cflat hello64 hello64_intel \
	      Hello64_intel.s Hello64_att.s Hello32_intel.s Hello32_att.s

# hello
hello: hello.o
	$(CC) -m32 -o $@ $^
hello.o: hello.s
	$(AS) $(ASFLAGS) --32 -o $@ $^
	file $@

# hello_cflat
hello_cflat: hello_cflat.o
	$(CC) -m32 -o $@ $^
hello_cflat.o: hello_cflat.asm
	$(AS) $(ASFLAGS) --32 -o $@ $^

# hello64
hello64.o: hello64.asm
	$(AS) $(ASFLAGS) -o $@ $^
	file $@

# hello64_intel
hello64_intel.o: hello64_intel.asm
	$(AS) $(ASFLAGS) -o $@ $^
	file $@

# Hello.s
Hello64_intel.s: Hello.c
	$(CC) $(CFLAGS) -m64 -masm=intel -S -o $@ $^
Hello64_att.s: Hello.c
	$(CC) $(CFLAGS) -m64 -masm=att   -S -o $@ $^
Hello32_intel.s: Hello.c
	$(CC) $(CFLAGS) -m32 -masm=intel -S -o $@ $^
Hello32_att.s: Hello.c
	$(CC) $(CFLAGS) -m32 -masm=intel -S -o $@ $^
