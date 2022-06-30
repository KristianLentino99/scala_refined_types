ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "refined_types_demo"
  )
libraryDependencies += "eu.timepit" %% "refined" % "0.9.15"
