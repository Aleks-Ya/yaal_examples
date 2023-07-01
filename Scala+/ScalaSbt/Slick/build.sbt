import Dependencies.{slickDep, slickHikaricpDep, slickTestkitDep, slf4jNopDep, h2Dep, scalaTestDep}

lazy val Slick = (project in file(".")).
  settings(libraryDependencies ++= Seq(slickDep, slickHikaricpDep, slickTestkitDep, slf4jNopDep, h2Dep, scalaTestDep))
