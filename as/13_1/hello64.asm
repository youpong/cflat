# gas att style for x86-64
LC0:
	.string	"Hello, World!"
	.globl	main
main:
	pushq	%rbp
	movq	%rsp, %rbp
	leaq	LC0(%rip), %rdi
	call	puts
	movq	$0, %rax
	movq	%rbp, %rsp
	popq	%rbp
	ret
