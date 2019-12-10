# gas x86-64
	.global main
LC0:
	.string	"Hello, World!\n"
main:
	pushq	%rbp
	movq	%rsp, %rbp
	leaq	LC0, %rdi
	call	printf
	movq	$0, %rax
	jmp	L0
L0:
	movq	%rbp, %rsp
	popq	%rbp
	ret
