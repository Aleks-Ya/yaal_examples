import Dependencies.{catsEffectScalaTestDep, fs2CoreDep, scalaTestDep}

lazy val Fs2Core = (project in file(".")).settings(
  libraryDependencies ++= Seq(fs2CoreDep, scalaTestDep, catsEffectScalaTestDep)
)
