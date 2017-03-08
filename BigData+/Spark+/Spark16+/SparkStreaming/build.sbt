import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "ru.yaal.examples.bigdata.spark",
      scalaVersion := "2.11.8",
      version      := "1"
    )),
    name := "SparkStreaming",
    libraryDependencies ++= allDeps,
	javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
	parallelExecution in Test := false,
	mainClass in assembly := Some("lesson1.Main"),
	assemblyJarName in assembly := "spark_streaming.jar"
  )
