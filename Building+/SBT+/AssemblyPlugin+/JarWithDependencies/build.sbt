import Dependencies._

lazy val root = (project in file(".")).
  settings(
	name := "JarWithDependencies",
	organization := "ru.yaal.build.sbt.plugin.assembly",
	scalaVersion := "2.11.8",
	version      := "1",
	libraryDependencies ++= allDeps,
	mainClass in assembly := Some("my.MyMain"),
	assemblyJarName in assembly := "fat.jar"
	//,
	//assemblyMergeStrategy in assembly := {
		//case x => MergeStrategy.rename
	//}
  )
