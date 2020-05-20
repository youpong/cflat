# FILES

* Makefile:	   This file.

* hello.s:           chapter 13.1 original hello.s (for x86)
* Hello.c:           equivalent in c source to assembly hello.s

* hello64.asm:       gas AT&T syntax for x86-64
* hello64_intel.asm: gas Intel syntax for x86-64

* hello_cflat.asm:   assembly which cflat compiles hello.cb

# How to build from hello.s
'''sh
$ make hello
'''