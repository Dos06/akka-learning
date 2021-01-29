package com.example

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import com.typesafe.config.{Config, ConfigFactory}
import com.example.actors.ItemActor
import com.example.models.ItemCommand
import com.example.routes.HttpRoutes
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object Boot extends App {
  implicit val config: Config = ConfigFactory.load()
  val log = LoggerFactory.getLogger(getClass)
  implicit val actorSystem = ActorSystem(Behaviors.empty, config.getString("akka.actor.system"))
  implicit def executionContext: ExecutionContext = actorSystem.executionContext
  val database = new Database()
  val itemActor: ActorSystem[ItemCommand] = ActorSystem(ItemActor(database), "database")
  val httpRoutes = new HttpRoutes(itemActor)

  runHttpServer() onComplete {
    case Success(serverBinding) =>
      log.info(s"ServiceName http server has been started. $serverBinding")

    case Failure(exception) =>
      throw exception
  }

  private def runHttpServer(): Future[Http.ServerBinding] = {
    Http().newServerAt(config.getString("http-server.interface"), port = config.getInt("http-server.port")).bind(httpRoutes.routes)
  }
}
