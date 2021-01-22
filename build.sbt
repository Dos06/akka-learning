name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.11"
val akkaHttpVersion = "10.1.11"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  // akka streams
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  // akka http
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,



  "com.lightbend.akka" %% "akka-stream-alpakka-mongodb" % "2.0.2",
//  "org.reactivemongo" %% "reactivemongo" % "1.0.2",
  "org.reactivemongo" %% "reactivemongo" % "0.20.13",
  "org.reactivemongo" %% "reactivemongo-akkastream" % "0.20.13",
  "org.reactivemongo" %% "reactivemongo-scalafix" % "1.0.2",

//  "org.reactivemongo" %% "reactivemongo" % "0.11.7",
  "com.typesafe.akka" %% "akka-http-core" % "10.2.3",
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.1.1",



  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,

//  "com.typesafe.akka" %% "akka-stream-experimental" % "1.0",
//  "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0",
//  "com.typesafe.akka" %% "akka-http-experimental" % "1.0",
//  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "1.0",
//  "org.reactivemongo" %% "reactivemongo" % "0.20.13",
//  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
//  "com.typesafe.akka" %% "akka-http-testkit-experimental" % "1.0"
)

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "Typesape" at "https://repo.typesafe.com/typesafe/releases/"
