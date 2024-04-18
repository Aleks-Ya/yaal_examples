import Dependencies.{http4sDslDep, http4sEmberClientDep, catsEffectScalaTestDep, scalaTestDep}

lazy val Http4sDsl = (project in file(".")).settings(
  libraryDependencies ++= Seq(http4sDslDep, http4sEmberClientDep, scalaTestDep, catsEffectScalaTestDep)
)
