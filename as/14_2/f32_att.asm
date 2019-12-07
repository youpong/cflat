; nasm x86 full assembly code
; AT&T syntax 
f:
	; variable table		
	; x ->  8(%ebp), y -> 12(%ebp)
	; i -> -4(%ebp), j -> -8(%ebp)
	
	; prologue
	pushl	%ebp
	movl	%esp, %ebp
	subl	$8, %esp	
	
	; i = x
	movl 	8(%ebp), -4(%ebp)
	
	; j = i * y
	movl	12(%ebp), %eax
	imull	-4(%ebp), %eax	
	movl	%eax, -8(%ebp)
		
	; set eax to return value
	movl	-8(%ebp), %eax
	
	; epilogue
	movl	%ebp, %esp
	popl	%ebp
	ret

main:
	; variable table
	; i -> -4(%ebp)
	
	; prologue
	pushl	%ebp
	movl	%esp, %ebp
	subl	$4, %esp
	
	; i = 77
	movl	$77, -4(%ebp)	
	
	; i = f(i, 8)
	pushl	$8		; push 2nd arg(8)
	movl	-4(%ebp), %eax
	pushl	%eax		; push 1st arg(i)
	call	f		; call f
	addl	$8, %esp	; stack clear
	movl	%eax, -4(%ebp)	; set i

	; i %= 5
	movl	$5, %ecx
	movl	-4(%ebp), %eax
	ctld			; signed expantion edx:eax from eax
	idivl	%ecx		; signed eax/ecx -> quatient eax, reminder edx
	movl	%edx, -4(%ebp)	; set i
	
	; set return value of function
	movl	-4(%ebp), %eax
	
	; epilogue
	movl	%ebp, %esp
	popl	%esp
	ret
