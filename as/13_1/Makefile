# Hello.c:           equivalent in c source to assembly hello.s
# hello.s:           13.1 hello.s(assembly which gcc compiles Hello.c)
# hello_cflat.asm:   assembly which cflat compiles hello.cb
# hello64.asm:       gas x86-64 AT&T syntax
# hello64_intel.asm: gas x86-64 intel syntax

LDFLAGS = -g -no-pie

.PHONY: all clean

all: hello_cflat hello64 hello64_intel
clean:
	rm -f a.out *.o \
              hello hello_cflat hello64 hello64_intel

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
