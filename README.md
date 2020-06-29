# Test
```
$ mvn test
```

# Package
```
$ mvn package
```

# Document
```
$ mvn javadoc:javadoc
```

# Usage
JAR file
```
$ java -jar target/cbc-XXX.jar [options] [files...]
options
--help          show help message
```

Class files
```
$ java -classpath target/classes cflat.compiler.Compiler [options] [files...]
```

# How to use debugger
```
$ jdb -classpath target/classes -sourcepath src/main/java:src/test/java cflat.compiler.Compiler [options] [files...]
```
