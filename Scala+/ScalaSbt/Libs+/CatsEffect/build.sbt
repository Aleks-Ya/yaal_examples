import Dependencies.{catsEffectDep, catsEffectScalaTestDep, scalaTestDep}

lazy val CatsEffect = (project in file("."))
  .dependsOn(Projects.UtilSrc)
  .settings(libraryDependencies ++= Seq(catsEffectDep, scalaTestDep, catsEffectScalaTestDep))
