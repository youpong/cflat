WORK_FILES = Adder.java AdderConstants.java AdderTokenManager.java \
		ParseException.java SimpleCharStream.java \
		Token.java TokenMgrError.java
TARGET = Adder.class
JAVAC = javac
JAVACC = javacc

.PHONY: all clean
.SUFFIXES: .class .java .jj


all: $(TARGET)
clean:
	rm -f *.class $(WORK_FILES)

.java.class:
	$(JAVAC) $<
.jj.java:
	$(JAVACC) $<
