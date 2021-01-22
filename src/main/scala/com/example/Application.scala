package com.example

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives._
import scalafix.internal.util.TypeExtractors.Nothing

object Application extends App {
  implicit val system = ActorSystem("A-system")
  implicit val materializer = ActorMaterializer
  implicit val ec = system.dispatcher

  var item1 = new Item(1, "i1", 100)
  var item2 = new Item(2, "i2", 200)
  var item3 = new Item(3, "i3", 300)

  var arr = Array(item1, item2, item3)

  val route =
    path("") {
      get {
        complete("Home page")
      }
    }~
    path("item") {
      post {
        parameter("id".as[Int], "name", "price".as[Int]) { (id, name, price) =>
          arr :+ new Item(id, name, price)
          complete("Item created")
        }
      }
    }~
    path("items") {
      get {
        complete {
          var str = ""
          for (v <- arr) {
            str += v.toString + "\n"
          }
          str
        }
      }
    }~
    pathPrefix("item") {
      concat(
        pathEnd {
          complete("/item")
        },
        path(IntNumber) { int =>
          complete(arr(int).toString)
        }
      )

    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
}

case class Item(var id: Int, var name: String, var price: Int) {
  override def toString: String = s"{\n\t'id' = $id,\n\t'name' = '$name',\n\t'price' = $price\n}"
}
