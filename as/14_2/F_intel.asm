; x86 version full source		
; これを nasm でアセンブルできるかなぁ。？ 

f:
	; variable table		
	; x ->  8(%ebp), y -> 12(%ebp)
	; i -> -4(%ebp), j -> -8(%ebp)
	
	; prologue
	push	ebp
	mov	ebp, esp
	sub	esp, 8
	
	; i = x
	mov 	eax, -4[ebp]
	mov	8[ebp], eax
	
	; j = i * y
	mov	eax, 12[ebp]
	imul	eax, -4[ebp]
	mov	-8[ebp], eax
		
	; set eax to return value
	mov	eax, -8[ebp]
	
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
	mov	-4[ebp], 77
	
	; i = f(i, 8)
	push	8		; push 2nd arg(8)
	mov	eax, -4[ebp]
	push	eax		; push 1st arg(i)
	call	f		; call f
	add	esp, 8 	; stack clear
	mov	-4[ebp], eax 	; set i

	; i %= 5
	mov	ecx, 5 
	mov	eax, -4[ebp]
	ctld			; signed expantion edx:eax from eax
	idiv	ecx		; signed eax/ecx -> quatient eax, reminder edx
	mov	-4[ebp], edx 	; set i
	
	; set return value of function
	mov	eax, -4[ebp]
	
	; epilogue
	mov	esp, ebp
	pop	esp
	ret
