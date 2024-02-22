import Dependencies.{catsEffectDep, catsEffectScalaTestDep, scalaTestDep}

lazy val CatsEffect = (project in file(".")).settings(
  libraryDependencies ++= Seq(catsEffectDep, scalaTestDep, catsEffectScalaTestDep)
)
