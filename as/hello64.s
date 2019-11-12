//	.file	"hello.cb"
	.section	.rodata
LC0:
	.string	"Hello, World!\n"
//	.text
	.global main
//	.type	main,@function
main:
	pushq	%rbp
	movq	%rsp, %rbp

//	movq	LC0, %rax 
	leaq	LC0, %rax
	pushq	%rax
	call	printf
	addq	$4, %rsp
	movq	$0, %rax
	jmp	L0
L0:
	movq	%rbp, %rsp
	popq	%rbp
	ret
	.size	main,.-main
	
	
