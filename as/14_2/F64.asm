	.global f
f:
	// variable table
	// x -> 16(%rbp), y ->  24(%rbp)
	// i -> -8(%rbp), j -> -16(%rbp)
	
	// prologue
	pushq	%rbp
	movq	%rsp, %rbp
	subq	$16, %rsp	
	
	// i = x
	movq	16(%rbp), %rax
	movq 	%rax, -8(%rbp)
	
	// j = i * y
	movq	24(%rbp), %rax
	imulq	-8(%rbp), %rax	
	movq	%rax, -16(%rbp)
		
	// set eax to return value
	movq	-16(%rbp), %rax
	
	// epilogue
	movq	%rbp, %rsp
	popq	%rbp
	ret

