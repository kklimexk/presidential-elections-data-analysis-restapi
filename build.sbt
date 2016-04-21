name := """presidential-elections-data-analysis-restapi"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.11",
  "org.twitter4j" % "twitter4j-core" % "4.0.0",
  "org.twitter4j" % "twitter4j-async" % "4.0.0",
  "org.twitter4j" % "twitter4j-stream" % "4.0.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
