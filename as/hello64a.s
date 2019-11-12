	.intel_syntax noprefix
	.global main	
msg:
	.string	"Hello"
main:	
	lea	rdi, msg[rip]
	call	puts
	mov	rax, 0
	ret
