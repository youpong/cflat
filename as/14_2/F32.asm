; nasm x86 
	global main
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

main:
	; variable table
	; i -> [ebp-4]
	
	; prologue
	push	ebp
	mov	ebp, esp
	sub	esp, 4
	
	; i = 77
	mov	dword [ebp-4], 77
	
	; i = f(i, 8)
	push	8		   ; push 2nd arg(8)
	mov	eax, dword [ebp-4]
	push	eax		   ; push 1st arg(i=77)
	call	f		   ; call f
	add	esp, 8		   ; stack clear
	mov	dword [ebp-4], eax ; set i

	; i %= 5
	mov	ecx, 5
	mov	eax, dword [ebp-4]
	cdq			   ; signed expantion edx:eax from eax
	idiv	ecx		   ; eax/ecx -> quatient eax, reminder edx
	mov	dword [ebp-4], edx ; set i
	
	; set return value of function(=i)
	mov	eax, dword [ebp-4]

	; epilogue
	mov	esp, ebp
	pop	ebp
	ret
