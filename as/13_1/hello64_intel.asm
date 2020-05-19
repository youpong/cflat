# gas intel_syntax for x86-64
	.intel_syntax noprefix
	.global main	
msg:
	.string	"Hello, World!"
main:
	push	rbp
	mov	rbp, rsp
	lea	rdi, msg[rip]
	call	puts
	mov	rax, 0
	mov	rsp, rbp
	pop	rbp
	ret
