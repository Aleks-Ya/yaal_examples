import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "ru.yaal.examples.bigdata.kafka",
      scalaVersion := "2.11.8",
      version := "1"
    )),
    name := "EmbeddedKafka",
    libraryDependencies ++= allDeps,
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
    parallelExecution in Test := false
  )
