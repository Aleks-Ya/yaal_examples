/**
  * Connecting to a Standalone Spark Master.
  */

import Dependencies.*

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "ru.yaal.examples.bigdata.spark3",
      scalaVersion := "2.12.17",
      version := "1"
    )),
    name := "Spark3CoreStandalone",
    libraryDependencies ++= allDeps,
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
    Test / parallelExecution := false,
    assembly / assemblyJarName := "fat.jar",
    assembly / assemblyMergeStrategy := {
      case x if x.startsWith("META-INF") => MergeStrategy.discard
      case _ => MergeStrategy.deduplicate
    },
    assembly / assemblyExcludedJars := {
      val cp = (assembly / fullClasspath).value
      cp filter {
        !_.data.getName.matches("spark*.")
      }
    }
  )
