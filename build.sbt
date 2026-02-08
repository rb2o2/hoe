ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging, LauncherJarPlugin)
  .settings(
    name := "halls",
    Compile / mainClass := Some("rb2o2.halls.gui.HoE"))

//resourceDirectory := baseDirectory.value / "src/main/resources"
//Universal / mainClass := Some("rb2o2.halls.gui.AppMain")

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"
