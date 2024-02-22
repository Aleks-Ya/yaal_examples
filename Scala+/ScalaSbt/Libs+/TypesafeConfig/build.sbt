import Dependencies.{scalaTestDep, typesafeConfigDep}

lazy val TypesafeConfig = (project in file(".")).settings(
  libraryDependencies ++= Seq(typesafeConfigDep, scalaTestDep),
  cancelable in Global := true
)
