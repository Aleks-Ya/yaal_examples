# Application for SBT Launch

Docs: https://www.scala-sbt.org/1.x/docs/Sbt-Launcher.html

## Run
1. Create the artifact: `sbt clean publishLocal`
2. Launch the app (from the project root): 
```shell script
java -jar launcher-1.1.4.jar @app.sbt.boot.properties arg1 arg2
#or
java -Dsbt.boot.properties=app.sbt.boot.properties -jar launcher-1.1.4.jar  arg1 arg2
```