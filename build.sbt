name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.11"
//val akkaHttpVersion = "10.1.11"
val akkaHttpVersion = "10.2.3"
val circeVersion = "0.13.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,

  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,

  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
//  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
//  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.3",

  "com.typesafe.akka" %% "akka-http-core" % "10.2.3",

//  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
//  "org.scalatest" %% "scalatest" % "3.1.0" % Test,

  "de.heikoseeberger" %% "akka-http-circe" % "1.29.1",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,

  "com.zaxxer" % "HikariCP" % "3.4.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",

  "com.typesafe.slick" %% "slick" % "3.3.3",
//  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.1",
  "org.slf4j" % "slf4j-nop" % "1.7.26",
//  "org.slf4j" % "slf4j-nop" % "1.6.4",

  "mysql" % "mysql-connector-java" % "8.0.23",

//  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalatest" %% "scalatest" % "3.2.2" % Test,
  "joda-time" % "joda-time" % "2.10.5"
)

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "Typesape" at "https://repo.typesafe.com/typesafe/releases/"
