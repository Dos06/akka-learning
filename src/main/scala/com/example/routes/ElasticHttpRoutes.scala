package com.example.routes

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.Directives.{complete, concat, get, pathEndOrSingleSlash, pathPrefix}
import akka.http.scaladsl.server.Route
import com.example.models.Item.ItemCommand
import com.sksamuel.elastic4s.ElasticClient
import com.typesafe.config.Config

import scala.concurrent.ExecutionContext

class ElasticHttpRoutes(elasticSearchClient: ElasticClient, itemActor: ActorSystem[ItemCommand])(implicit system: ActorSystem[_],
                                                            implicit val executionContext: ExecutionContext,
                                                            config: Config) {

  val routes: Route = pathPrefix("/") {
    concat(
      pathEndOrSingleSlash {
        get {
          complete("Application is running")
        }
      },
      new ElasticItemRoutes(elasticSearchClient, itemActor).routes
    )
  }

}
