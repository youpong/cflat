; nasm
	global f
; int f(int x, int y)		
f:
	; variable table
	;args  x -> edi,      y -> esi
	;local i -> [rbp-4], j -> [rbp-8]
	
	; prologue
	push	rbp		
	mov	rbp, rsp
	sub	rsp, 8
	
	; i = x
	mov 	dword [rbp-4], edi
	
	; j = i * y
	imul	esi, dword [rbp-4]
	mov	dword [rbp-8], esi
	
	; set eax to return value
	mov	eax, dword [rbp-8]

	; epilogue
	mov	rsp, rbp
	pop	rbp
	ret
