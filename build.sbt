organization := "me"

lazy val berout = (project in file("."))
  .settings(
    name := "berout",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )
