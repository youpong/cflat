LDFLAGS = -g

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
