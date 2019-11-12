/* x86-64 intel_syntax minimum */
	.intel_syntax noprefix
	.global main	
msg:
	.string	"Hello\n"
main:
	push	rbp
	mov	rbp, rsp
	lea	rdi, msg[rip]
	call	printf
	mov	rax, 0
	mov	rsp, rbp
	pop	rbp
	ret
