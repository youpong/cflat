# 14.2 のサンプル assembly code を assemble する。
#
# f.c:          original c source code

AS = as
ASFLAGS = -g
CFLAGS = -O0

.PHONY: all clean

all: f
clean:
	rm -f a.out *.o *.s f

# f
f: f.o
	$(CC) -m32 -o $@ $^
f.o: f.s
	$(AS) $(ASFLAGS) --32 -o $@ $^
f.s: f.c
	$(CC) $(CFLAGS) -S -m32 $^

