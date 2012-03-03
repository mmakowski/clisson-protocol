organization := "com.bimbr"

name := "clisson-protocol"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "com.google.code.gson"  % "gson"        % "2.1",
  "junit"                 % "junit"       % "4.10"  % "test", 
  "org.mockito"           % "mockito-all" % "1.9.0" % "test",
  "org.specs2"           %% "specs2"      % "1.8.2" % "test"
)

crossPaths := false
