# print all label then delete from pattern space
/^[ \t]*[^"]+:/ { 
    p
    d
}
# print directive ".globl"
/^[ \t]*\.globl/ {
    p
}
# delete all directive on pattern space
/^[ \t]*\./ {
    d
}
# here. print string still on pattern space