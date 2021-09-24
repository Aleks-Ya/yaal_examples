import Dependencies.{providedDeps, _}

lazy val root = (project in file(".")).
  settings(
    name := "HdfsScala",
    inThisBuild(List(
      organization := "ru.yaal.examples.hadoop.hdfs",
      scalaVersion := "2.11.8",
      version := "1"
    )),
    libraryDependencies ++= compileDeps union providedDeps.map(_ % "provided") union testDeps,
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
    parallelExecution in Test := false
  )
