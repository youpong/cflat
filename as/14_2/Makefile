# 14.2 のサンプル assembly code を assemble する。
#
# f.c:          original c source code

AS = as
ASFLAGS = -g
CC = gcc
CFLAGS = -O0 -fcf-protection=none -fno-pie
# below doesn't work
#CFLAGS = -O0 -fcf-protection=none -pie
#CFLAGS = -O0 -fcf-protection=none -fpie
#CFLAGS = -O0 -fcf-protection=none -no-pie

.PHONY: all clean

all: f
clean:
	rm -f a.out *.o *.s f *.tmp

# f
f: f.o
	$(CC) -m32 -o $@ $^
f.o: f.s
	$(AS) $(ASFLAGS) --32 -o $@ $^
f.s: f.c
	$(CC) $(CFLAGS) -S -m32 $^
	cp $@ $@.tmp
	#sed -r -f filter.sed $@.tmp >$@
	sed -i -r -f filter.sed $@
