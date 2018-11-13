name := "spark-version-verification"

version := "0.1"


lazy val root = project
  .in(file("."))
  .settings(commonSettings: _*)
  .settings(
    publishArtifact := false
  )
  .aggregate(common, spark16, spark22)

lazy val common =
  project.in(file("common"))
    .settings(commonSettings: _*)
    .settings(scalaVersion := "2.10.6")
    .settings(libraryDependencies ++= Dependencies.commonDependencies)

//lazy val commonDependency = common % "compile->compile;test->test"

lazy val spark16 =
  project
  .in(file("spark16"))
  .settings(commonSettings: _*)
  .settings(scalaVersion := "2.10.6")
  .settings(libraryDependencies ++= Dependencies.spark16Dependencies)
  .dependsOn(common)

lazy val spark22 = project
  .in(file("spark22"))
  .settings(commonSettings: _*)
  .settings(scalaVersion := "2.11.12")
  .settings(libraryDependencies ++= Dependencies.spark22Dependencies)
  .dependsOn(common)

lazy val commonSettings = Seq(
  organization := "jp.gr.java_conf.pesk",
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint", "-encoding", "UTF-8"),
  scalacOptions := Seq("-unchecked", "-deprecation", "-feature")
)
