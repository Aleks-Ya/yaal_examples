import Dependencies._

lazy val root = (project in file(".")).
  settings(
	name := "JarWithDependencies",
	organization := "ru.yaal.build.sbt.plugin.assembly",
	scalaVersion := "2.12.17",
	version      := "1",
	libraryDependencies ++= allDeps,
	assembly / mainClass  := Some("my.MyMain"),
	assembly / assemblyJarName := "fat.jar"
)
