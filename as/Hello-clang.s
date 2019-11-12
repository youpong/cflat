	.text
	.intel_syntax noprefix
	.file	"Hello.c"
	.globl	main                    # -- Begin function main
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# %bb.0:
	push	rax
	.cfi_def_cfa_offset 16
	mov	edi, offset .L.str
	call	puts
	xor	eax, eax
	pop	rcx
	.cfi_def_cfa_offset 8
	ret
.Lfunc_end0:
	.size	main, .Lfunc_end0-main
	.cfi_endproc
                                        # -- End function
	.type	.L.str,@object          # @.str
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"Hello"
	.size	.L.str, 6


	.ident	"clang version 8.0.0-3 (tags/RELEASE_800/final)"
	.section	".note.GNU-stack","",@progbits
	.addrsig
