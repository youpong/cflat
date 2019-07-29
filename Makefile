AUTO_FILES = Adder.java AdderConstants.java AdderTokenManager.java \
		ParseException.java SimpleCharStream.java \
		Token.java TokenMgrError.java
clean:
	rm -f *.class $(AUTO_FILES)
