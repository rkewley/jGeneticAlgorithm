name := "jGeneticAlgorith"

organization := "DMF"

version := "0.1.0-SNAPSHOT"

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false


libraryDependencies ++= Seq(
  "colt" % "colt" % "1.2.0"
)


