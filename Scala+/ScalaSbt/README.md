# ScalaSbt

## Build
Choose Java 11 version: `sdk use java 11.0.29-zulu`
Choose Scala 2.12 version: `sdk use scala 2.12.20`
Choose SBT latest version: `sdk use sbt 1.11.7`
Compile sources: `sbt Compile/clean Compile/compile`  
Compile tests: `sbt Test/clean Test/compile`

## Errors
### StorageUtils cannot access class DirectBuffer
Command: run test `dataframe.create.json.ReadJsonFileTest`
Message:
```
An exception or error caused a run to abort: class org.apache.spark.storage.StorageUtils$ 
(in unnamed module @0x51e5fc98) cannot access class sun.nio.ch.DirectBuffer (in module java.base) 
because module java.base does not export sun.nio.ch to unnamed module @0x51e5fc98
```
Solution: set Java 8 as the Project JDK 

###  invalid source release: 21
Command: run test `dataframe.create.json.ReadJsonFileTest`
Message:
```
scala: Compilation failed when compiling to: /home/aleks/pr/home/yaal_examples/Scala+/ScalaSbt/Spark+/Spark3+/Spark3Sql/target/scala-2.12/classes
  invalid source release: 21
  com.sun.tools.javac.main.OptionHelper$GrumpyHelper.error(OptionHelper.java:103)
```
Solution: set Java 8 as the SBT JRE in IntelliJ Idea Settings 
