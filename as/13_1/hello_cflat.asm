# gas x86
# may be output which cflat compile hello.cb
	.file	"hello.cb"
	.section	.rodata
LC0:
	.string	"Hello, World!\n"
	.text
	.global main
	.type	main,@function
main:
	pushl	%ebp
	movl	%esp, %ebp
	movl	$LC0, %eax
	pushl	%eax
	call	printf
	addl	$4, %esp
	movl	$0, %eax
	jmp	L0
L0:
	movl	%ebp, %esp
	popl	%ebp
	ret
	.size	main,.-main
