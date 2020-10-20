import Dependencies.scalaTestDep

val slickVersion = "3.3.3"

lazy val Slick = (project in file(".")).
  settings(
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % slickVersion,
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
      "com.h2database" % "h2" % "1.4.200",
      scalaTestDep)
  )
