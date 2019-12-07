; nasm
extern f	
global main
main:
	; variable table
	; i -> [rbp-4]
	
	; prologue
	push	rbp
	mov	rbp, rsp
	sub	rsp, 4
	
	; i = 77
	mov	dword [rbp-4], 77	
	
	; i = f(i, 8)
	mov	esi, 8
	mov	edi, dword [rbp-4]
	call	f
	mov	dword [rbp-4], eax

	; i %= 5
	mov	eax, dword [rbp-4]	
	mov	ecx, 5
	cdq
	idiv	ecx		
	mov	dword [rbp-4], edx	
	
	; set return value of function
	mov	eax, dword [rbp-4]
	
	; epilogue
	mov	rsp, rbp
	pop	rbp
	ret
