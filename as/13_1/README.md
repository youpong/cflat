# FILES

* Makefile:	   This file.

* hello.s:           chapter 13.1 original hello.s (for x86)
* Hello.c:           equivalent in c source to assembly hello.s

* hello64.asm:       gas AT&T syntax for x86-64
* hello64_intel.asm: gas Intel syntax for x86-64

* hello_cflat.asm:   assembly which cflat compiles hello.cb

# How to build

```
$ make hello
...
file hello.o
hello.o: ELF 32-bit LSB relocatable, Intel 80386, version 1 (SYSV), with debug_info, not stripped
...
```

# How to debug

To debug type like bellow.
```
$ gdb ./hello
```

# How to output assembly in various Arch, style

produce assembly code in various arch, style from Hello.c, sample C source.

```
$ make all_assembly
```
produce
* Hello64_intel.s: intel style for x86-64
* Hello64_att.s    att style for x86-64
* Hello32_intel.s  intel style for x86
* Hello32_att.s    att style for x86

# URLs

PIE

* https://stackoverflow.com/questions/52344336/gas-asm-pie-x86-64-access-variable-with-lea-instruction
