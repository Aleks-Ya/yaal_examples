# spark-shell CLI

## Install
### By Python
Install: `pip install pyspark`
Upgrade to latest version: `pip install pyspark -U`
### From ZIP
1. Download Spark distributive: https://spark.apache.org/downloads.html
2. Unzip
3. Choose Java 8, e.g.: `sdk use java 8.0.302-open`
4. Run `./bin/spark-shell`

## Commands
Help: `spark-shell -h`
Show version: `spark-shell --version`
Run in local mode: `spark-shell --master local[2]`
Connect to existing Master: `spark-shell --master spark://spark-standalone-cluster-master:7077`
Set DEBUG log level: `sc.setLogLevel("DEBUG")`
Load functions from a Scala file: `:load /path/to/functions.scala`

## Commands in `scala>`
Help: `:help`
Help about a command: `:help :type` or `:help type`
Show completions for a command: `:completions sc.setL`
Exit: `:quit`
All:
```
All commands can be abbreviated, e.g., :he instead of :help.
:completions <string>    output completions for the given string
:edit <id>|<line>        edit history
:help [command]          print this summary or command-specific help
:history [num]           show the history (optional num is commands to show)
:h? <string>             search the history
:imports [name name ...] show import history, identifying sources of names
:implicits [-v]          show the implicits in scope
:javap <path|class>      disassemble a file or class name
:line <id>|<line>        place line(s) at the end of history
:load <path>             interpret lines in a file
:paste [-raw] [path]     enter paste mode or paste a file
:power                   enable power user mode
:quit                    exit the interpreter
:replay [options]        reset the repl and replay all previous commands
:require <path>          add a jar to the classpath
:reset [options]         reset the repl to its initial state, forgetting all session entries
:save <path>             save replayable session to a file
:sh <command line>       run a shell command (result is implicitly => List[String])
:settings <options>      update compiler options, if possible; see reset
:silent                  disable/enable automatic printing of results
:type [-v] <expr>        display the type of an expression without evaluating it
:kind [-v] <type>        display the kind of a type. see also :help kind
:warnings                show the suppressed warnings from the most recent line which had any
```

## Test calculations in Shell
1. Test Spark Core: `sc.parallelize(Seq(1, 2, 3)).collect()`
2. Test Spark SQL:
```Scala
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
val schema = StructType(StructField("name", StringType) :: StructField("age", IntegerType) :: Nil)
val rdd = sc.parallelize(Seq(Row("Jhon", 25), Row("Peter", 35)))
val peopleDf = spark.sqlContext.createDataFrame(rdd, schema)
peopleDf.show
```
