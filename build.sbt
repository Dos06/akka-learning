name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.11"
//val akkaHttpVersion = "10.1.11"
val akkaHttpVersion = "10.2.3"
val circeVersion = "0.13.0"
val akkaPersistenceCassandraVersion = "1.0.3"
val akkaProjectionVersion = "1.0.0"
val swaggerVersion = "2.1.1"
lazy val elastic4sVersion = "7.11.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,

  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,

  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

  "com.typesafe.akka" %% "akka-http-core" % "10.2.3",

  "com.typesafe.akka" %% "akka-cluster-sharding-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-query" % akkaVersion,
  "com.typesafe.akka" %% "akka-serialization-jackson" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-cassandra" % akkaPersistenceCassandraVersion,
  "com.typesafe.akka" %% "akka-persistence-cassandra-launcher" % akkaPersistenceCassandraVersion,

  "com.lightbend.akka" %% "akka-projection-eventsourced" % akkaProjectionVersion,
  "com.lightbend.akka" %% "akka-projection-cassandra" % akkaProjectionVersion,

  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-persistence-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,

  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",

  "com.github.swagger-akka-http" %% "swagger-akka-http" % "2.0.4",
  "com.github.swagger-akka-http" %% "swagger-scala-module" % "2.0.6",
  "io.swagger.core.v3" % "swagger-core" % swaggerVersion,
  "io.swagger.core.v3" % "swagger-annotations" % swaggerVersion,
  "io.swagger.core.v3" % "swagger-models" % swaggerVersion,
  "io.swagger.core.v3" % "swagger-jaxrs2" % swaggerVersion,
  "javax.ws.rs" % "javax.ws.rs-api" % "2.0.1",

  "de.heikoseeberger" %% "akka-http-circe" % "1.29.1",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,

  "com.zaxxer" % "HikariCP" % "3.4.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",

  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.slf4j" % "slf4j-nop" % "1.7.26",

  "mysql" % "mysql-connector-java" % "8.0.23",

  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalatest" %% "scalatest" % "3.2.2" % Test,
  "joda-time" % "joda-time" % "2.10.5",
  "com.lightbend.akka" %% "akka-projection-testkit" % akkaProjectionVersion % Test,
  "commons-io" % "commons-io" % "2.4" % Test,

  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-json-circe" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-client-akka" % elastic4sVersion,
)

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "Typesape" at "https://repo.typesafe.com/typesafe/releases/"

scalacOptions in Compile ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlog-reflective-calls", "-Xlint")
javacOptions in Compile ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

fork in run := false
parallelExecution in Test := false
logBuffered in Test := false

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

packageName in Universal := "app"

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
