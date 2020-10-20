import Dependencies.{h2Dep, scalaTestDep, slf4jNopDep}

val slickVersion = "3.3.3"

lazy val Slick = (project in file(".")).
  settings(
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % slickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
      "com.typesafe.slick" %% "slick-testkit" % slickVersion % Test,
      slf4jNopDep, h2Dep, scalaTestDep)
  )
