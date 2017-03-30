import Dependencies._

lazy val root = (project in file(".")).
  settings(
    name := "MapReduceScala",
    inThisBuild(List(
      organization := "ru.yaal.examples.hadoop",
      scalaVersion := "2.11.8",
      version := "1"
    )),
    libraryDependencies ++= allDeps,
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
    parallelExecution in Test := false
  )