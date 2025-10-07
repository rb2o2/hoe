ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging, LauncherJarPlugin)
  .settings(
    name := "halls",
    Compile / mainClass := Some("rb2o2.halls.gui.HoE"))


//Universal / mainClass := Some("rb2o2.halls.gui.AppMain")