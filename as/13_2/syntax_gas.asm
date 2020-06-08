# GNU as(gas) intel style syntax for x86-64
	
# Multiline Comments
# You can comment multiple lines using /* and */ as follows -
	
/* Single line comments
 * It starts with # character and they extend from to the end of
 * the line as preceded - 
 */

	.intel_syntax noprefix	# directive
	.global main
main:				# label
	
	mov rax, 42 		# instruction
	ret
