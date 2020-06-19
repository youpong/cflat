$ mvn test
$ mvn package
$ mvn javadoc:javadoc

$ java -jar target/cbc-XXX.jar [options] [files...]
options
--help          show help message

$ java -classpath target/classes cflat.compiler.Compiler [options] [files...]
$ jdb -classpath target/classes cflat.compiler.Compiler [options] [files...]