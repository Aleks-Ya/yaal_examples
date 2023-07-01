import Dependencies.{typesafeConfigDep, scalaTestDep}

lazy val TypesafeConfig = (project in file(".")).settings(
  libraryDependencies ++= Seq(typesafeConfigDep, scalaTestDep),
  cancelable in Global := true
  //  ,
  //  run / fork := true
)
