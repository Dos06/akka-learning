package com.example.routes

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.Directives.{complete, concat, get, pathEndOrSingleSlash, pathPrefix}
import akka.http.scaladsl.server.Route
import com.example.models.ItemCommand
import com.typesafe.config.Config

class HttpRoutes(itemActor: ActorSystem[ItemCommand])(implicit system: ActorSystem[_], config: Config) {
  val routes: Route = {
      concat(
        pathEndOrSingleSlash {
          get {
            complete("Home page")
          }
        },
        new ItemRoutes(itemActor).routes
      )
  }
}
