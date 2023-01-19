import sbt._

object Dependencies {
	val allDeps = Seq(
		"com.google.guava" % "guava" % "31.1-jre",
		"org.apache.commons" % "commons-lang3" % "3.12.0" % "provided",
		"junit" % "junit" % "4.13.2" % "test"
	)
}