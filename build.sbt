name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.11"
val akkaHttpVersion = "10.1.11"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,

  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
//  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.3",



  "com.lightbend.akka" %% "akka-stream-alpakka-mongodb" % "2.0.2",
//  "org.reactivemongo" %% "reactivemongo" % "1.0.2",
  "org.reactivemongo" %% "reactivemongo" % "0.20.13",
  "org.reactivemongo" %% "reactivemongo-akkastream" % "0.20.13",
  "org.reactivemongo" %% "reactivemongo-scalafix" % "1.0.2",

  "com.typesafe.akka" %% "akka-http-core" % "10.2.3",
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.1.1",

  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test
)

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "Typesape" at "https://repo.typesafe.com/typesafe/releases/"
