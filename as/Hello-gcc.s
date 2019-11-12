	.intel_syntax noprefix
	.text
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"Hello,world!"
	.section	.text.startup,"ax",@progbits
	.globl	main
main:
	push	rax
	#	lea	rdi, .LC0[rip]
	mov	edi, offset .LC0
	call	puts@PLT
	xor	eax, eax
	pop	rdx
	ret
