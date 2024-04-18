import Dependencies.{catsEffectScalaTestDep, http4sEmberClientDep, scalaTestDep}

lazy val Http4s = (project in file(".")).settings(
  libraryDependencies ++= Seq(http4sEmberClientDep, scalaTestDep, catsEffectScalaTestDep)
)
