

lazy val ScalaTestJsonAssert = (project in file("."))
  .settings(libraryDependencies ++= Seq("com.stephenn" %% "scalatest-json-jsonassert" % "0.2.5"))
