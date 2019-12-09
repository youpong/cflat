# gas x86 att style
# 13_1 hello.s	
	.file	"hello.c"
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"Hello, World!"
	.text
.globl	main
	.type	main, @function
main:
	leal	4(%esp), %ecx
	andl	$-16, %esp
	pushl	-4(%ecx)
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ecx
	subl	$16, %esp
	pushl	$.LC0
	call	puts
	movl	-4(%ebp), %ecx
	xorl	%eax, %eax
	leave
	leal	-4(%ecx), %esp
	ret
	.size	main, .-main
	.ident	"GCC: (Ubuntu 9.2.1-9ubuntu2) 9.2.1 20191008"
	.section	.note.GNU-stack,"",@progbits
