; nasm x86 
global main
f:
	; variable table		
	; x -> [ebp+8], y -> [ebp+12]
	; i -> [ebp-4], j -> [ebp-8]
	
	; prologue
	push	ebp
	mov	ebp, esp
	sub	esp, 8
	
	; i = x
	mov 	eax, [ebp-4]
	mov	[ebp+8], eax
	
	; j = i * y
	mov	eax, [ebp+12]
	imul	eax, [ebp-4]
	mov	[ebp-8], eax
		
	; set eax to return value
	mov	eax, [ebp-8]
	
	; epilogue
	mov	esp, ebp
	pop	ebp
	ret

main:
	; variable table
	; i -> -4(%ebp)
	
	; prologue
	push	ebp
	mov	ebp, esp
	sub	esp, 4
	
	; i = 77
	mov	dword [ebp-4], 77
	
	; i = f(i, 8)
	push	8		; push 2nd arg(8)
	mov	eax, [ebp-4]
	push	eax		; push 1st arg(i)
	call	f		; call f
	add	esp, 8 	; stack clear
	mov	[ebp-4], eax 	; set i

	; i %= 5
	mov	ecx, 5 
	mov	eax, [ebp-4]
	cdq			; signed expantion edx:eax from eax
	idiv	ecx		; signed eax/ecx -> quatient eax, reminder edx
	mov	[ebp-4], edx 	; set i
	
	; set return value of function
	mov	eax, [ebp-4]
	
	; epilogue
	mov	esp, ebp
	pop	esp
	ret
