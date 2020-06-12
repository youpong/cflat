# label
/^[ \t]*[^"]+:/ { 
    p
    d
}
# directive to print
/^[ \t]*\.globl/ {
    p
    d
}
# directive to cut
/^[ \t]*\./ {
	d
}