import Dependencies._

lazy val TypesafeConfig = (project in file(".")).settings(
  libraryDependencies ++= Seq(typesafeConfigDep, scalaTestDep),
  cancelable in Global := true
//  ,
//  fork in run := true
)