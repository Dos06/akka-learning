package com.example

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._

object Application extends App {
  implicit val system = ActorSystem("A-system")
  implicit val materializer = ActorMaterializer
  implicit val ec = system.dispatcher

  val route =
    path("") {
      get {
        complete("Home page")
      }
    }
    path("item") {
      post {

      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
}
