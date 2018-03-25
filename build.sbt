import sbt.Keys._
import sbt._

name := "NearestNeighbourAlgorithm"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies += "org.scala-lang" % "scala-library" % "2.12.4"

lazy val root = (project in file("."))
  .settings(name := "root")
  .settings(
    libraryDependencies += "com.github.yannrichet" % "JMathPlot" % "1.0",
    libraryDependencies += "com.github.yannrichet" % "JMathIO" % "1.0",
    libraryDependencies += "com.github.yannrichet" % "JMathArray" % "1.0"
  )