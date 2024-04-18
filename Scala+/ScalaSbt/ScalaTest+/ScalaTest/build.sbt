import Dependencies.scalaTestDep

lazy val ScalaTest = (project in file("."))
  .dependsOn(Projects.UtilSrc)
  .settings(libraryDependencies ++= Seq(scalaTestDep))
