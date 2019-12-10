# gas x86-64 intel_syntax 
	.intel_syntax noprefix
	.global main	
msg:
	.string	"Hello\n"
main:
	push	rbp
	mov	rbp, rsp
	lea	rdi, msg
	call	printf
	mov	rax, 0
	mov	rsp, rbp
	pop	rbp
	ret
