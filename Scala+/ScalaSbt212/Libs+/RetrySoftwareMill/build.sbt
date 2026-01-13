import Dependencies.{retrySoftwareMillDep, scalaTestDep}

lazy val RetrySoftwareMill = (project in file("."))
  .dependsOn(Projects.UtilSrc)
  .settings(libraryDependencies ++= Seq(retrySoftwareMillDep, scalaTestDep))
