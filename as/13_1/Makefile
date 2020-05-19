# linkder option without -no-pie cause error:
# --- 8< ---
# cc -g -fPIE  hello64.o   -o hello64
# /usr/bin/ld: hello64.o: relocation R_X86_64_32S against `.text' can not be used when making a PIE object; recompile with -fPIE
# collect2: error: ld returned 1 exit status
# make: *** [<builtin>: hello64] Error 1
# --- 8< ---

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
