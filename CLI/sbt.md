# sbt CLI

## Run

Run app: `sbt run`
Run sub-project:
```
sbt "project ScalaRun" run
sbt> ;project ScalaRun; run
```

## Compile

Compile project: `sbt compile`
Compile tests: `sbt test:compile`

## Test

Enter Continuous Build/Test: `sbt ~testQuick`
Run all tests: `sbt test`
Run test with name "my.Test": 
```
sbt "testOnly my.Test"
sbt> testOnly my.Test
```

## dependencyTree

Show artifact dependency tree (to a file):
```
# For "compile" configuration:
sbt dependencyTree > target/tree.txt
sbt compile:dependencyTree > target/tree.txt

# For "test" configuration:
sbt test:dependencyTree > target/tree.txt
```

## Other

Download sources and javadocs: `sbt update-classifiers`
Read Build Definition from file: `sbt reload`
Runs Scala Interpretator:  `sbt console`
Show SBT version: `sbt sbtVersion`
Execute the last command again: `sbt !!`
Clean work directory: `sbt clean`
Combines multiple commands: `sbt "; clean; compile; run"`
Package current project: `sbt package`
Create fat jar: `sbt assembly`
Show task dependency tree: `sbt "inspect tree clean"`
Set DEBUG log level: `sbt --debug sbtVersion`
