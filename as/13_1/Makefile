LDFLAGS = -g
ASFLAGS = -g

.PHONY: all clean

all: hello hello_cflat hello64 hello64_intel
clean:
	rm -f a.out *.o \
              hello hello_cflat hello64 hello64_intel

# hello
hello: hello.o
	$(CC) $(LDFLAGS) -m32 -o $@ $^
hello.o: hello.s
	$(AS) $(ASFLAGS) --32 -o $@ $^

# hello_cflat
hello_cflat: hello_cflat.o
	$(CC) $(LDFLAGS) -m32 -o $@ $^
hello_cflat.o: hello_cflat.asm
	$(AS) $(ASFLAGS) --32 -o $@ $^

# hello64
hello64.o: hello64.asm
	$(AS) $(ASFLAGS) -o $@ $^

# hello64_intel
hello64_intel.o: hello64_intel.asm
	$(AS) $(ASFLAGS) -o $@ $^
