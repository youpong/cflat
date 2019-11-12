clean:
	rm -f a.out *.o Hello Hello.s \
              hello \
              hello64 \
              hello64a
Hello: Hello.o
	$(CC) -o Hello Hello.o
Hello.o: Hello.s
	$(AS) -o Hello.o Hello.s
Hello.s: Hello.c
	$(CC) -Os -S -masm=intel Hello.c

hello: hello.o
	$(CC) -o hello hello.o
hello.o: hello.s
	$(AS) -o hello.o hello.s
hello64: hello64.o
	$(CC) -o hello64 hello64.o
hello64.o: hello64.s
	$(AS) -o hello64.o hello64.s
