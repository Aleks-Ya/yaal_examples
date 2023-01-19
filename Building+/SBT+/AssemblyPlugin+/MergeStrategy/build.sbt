import Dependencies._

lazy val root = (project in file(".")).
  settings(
    name := "MergeStrategy",
    organization := "ru.yaal.build.sbt.plugin.assembly",
    scalaVersion := "2.12.17",
    version := "1",
    libraryDependencies ++= allDeps,
    assembly / mainClass := Some("my.MyMain"),
    assembly / assemblyJarName := "fat.jar",
    assembly / assemblyMergeStrategy := {
      case x if x.startsWith("META-INF") => MergeStrategy.discard
      case "org/pcap4j/packet/factory/StaticUnknownPacketFactory.class" => MergeStrategy.first
      case _ => MergeStrategy.deduplicate
    }
  )
