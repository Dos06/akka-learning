package com.example

import com.typesafe.config.ConfigFactory
import reactivemongo.api.MongoDriver
import reactivemongo.core.nodeset.Authenticate

import scala.collection.JavaConverters._
//import scala.concurrent.ExecutionContext.Implicits.global

object MongoDB {
  val config = ConfigFactory.load()
  val database = config.getString("mongodb.database")
  val servers = config.getStringList("mongodb.servers").asScala
  val credentials = List(Authenticate(database,config.getString("mongodb.userName"), config.getString("mongodb.password")))

  val driver = new MongoDriver()
//  val connection = driver.connection(servers, authentications = credentials)
  val connection = driver.connection(servers)

  val db = connection(database)
}
