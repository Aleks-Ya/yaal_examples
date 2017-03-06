import sbt._

object Dependencies {
	val allDeps = Seq(
		"com.google.guava" % "guava" % "19.0",
		"org.apache.commons" % "commons-lang3" % "3.4" % "provided",
		"junit" % "junit" % "4.12" % "test"
	)
}