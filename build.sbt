name := """security-api"""

version := "1.0-SNAPSHOT"


lazy val root = (project in file(".")).enablePlugins(PlayJava)

Defaults.itSettings

scalaVersion := "2.11.7"



libraryDependencies ++= Seq(
  "junit" % "junit" % "4.12" % "test"
)


