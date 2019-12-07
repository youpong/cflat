; nasm x86
global f	
; int f(int x, int y)	
f:
	; variable table		
	; args  x -> [ebp+8], y -> [ebp+12]
	; local i -> [ebp-4], j -> [ebp-8]
	
	; prologue
	push	ebp
	mov	ebp, esp
	sub	esp, 8
	
	; i = x
	mov 	eax, dword [ebp+8]
	mov	dword [ebp-4], eax
	
	; j = i * y
	mov	eax, dword [ebp-4]
	mov	edi, dword [ebp+12]
	imul	edi
	mov	dword [ebp-8], eax
		
	; set eax to return value
	mov	eax, dword [ebp-8]

	; epilogue
	mov	esp, ebp
	pop	ebp
	ret
