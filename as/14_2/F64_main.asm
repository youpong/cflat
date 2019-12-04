	.global main
main:
	// variable table
	// i -> -8(%rbp)
	
	// prologue
	pushq	%rbp
	movq	%rsp, %rbp
	subq	$8, %rsp
	
	// i = 77
	movq	$77, -8(%rbp)	
	
	// i = f(i, 8)
	pushq	$8		
	movq	-8(%rbp), %rax
	pushq	%rax
	call	f
	addq	$16, %rsp		
	movq	%rax, -8(%rbp)	

	// i %= 5
	movq	$5, %rcx
	movq	-8(%rbp), %rax
	cltd
	idivq	%rcx		
	movq	%rdx, -8(%rbp)	
	
	// set return value of function
	movq	-8(%rbp), %rax
	
	// epilogue
	movq	%rbp, %rsp
	popq	%rbp
	ret

