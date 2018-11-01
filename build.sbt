
lazy val berout = (project in file("."))
  .settings(
    organization := "me",
    name := "berout",
    version := "0.1.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )
