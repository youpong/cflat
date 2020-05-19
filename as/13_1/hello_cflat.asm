# gas for x86
# may be output which cflat compile hello.cb
	.file	"hello.cb"
LC0:
	.string	"Hello, World!!"
	.global main
main:
	pushl	%ebp
	movl	%esp, %ebp
	leal	LC0, %eax
	pushl	%eax
	call	puts
	addl	$4, %esp
	movl	$0, %eax
	movl	%ebp, %esp
	popl	%ebp
	ret
