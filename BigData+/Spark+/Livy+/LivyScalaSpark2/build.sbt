/**
 * Connecting to a Standalone Spark Master.
 */

import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "ru.yaal.examples.bigdata.spark.livy",
      scalaVersion := scalaVer,
      version := "1"
    )),
    name := "LivyScalaSpark2",
    libraryDependencies ++= allDeps,
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
    parallelExecution in Test := false,
    assemblyJarName in assembly := "livy-scala-fat.jar",
    assemblyMergeStrategy in assembly := {
      case x if x.startsWith("META-INF") => MergeStrategy.discard
      case _ => MergeStrategy.deduplicate
    },
    assemblyExcludedJars in assembly := {
      val cp = (fullClasspath in assembly).value
      cp filter {
        !_.data.getName.matches("spark*.")
      }
    }
  )
