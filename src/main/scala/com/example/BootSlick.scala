package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.example.models.{Item, ItemTable}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

object BootSlick extends App {
  val db = Database.forConfig("mysql")
  lazy val items = TableQuery[ItemTable]

  db.run(items.schema.createIfNotExists)

  implicit val system = ActorSystem("A-system")
  implicit val materializer = ActorMaterializer
  implicit val ec = system.dispatcher

  var arr = Array[Item]()

  val route =
    path("") {
      get {
        complete("Home page")
      }
    }~
    path("item") {
      post {
        parameter("name", "price".as[Int]) { (name, price) =>
          db.run(items += Item(0, name, price))
          complete("Item created")
        }
      }
    }~
    pathPrefix("delete") {
      concat(
        pathEnd {
          complete("/item")
        },
        path(IntNumber) { int =>
          delete {
            val item = items.filter(_.id === int)
            val action = item.delete
            val affectedRowsCount: Future[Int] = db.run(action)
            val sql = action.statements.head

            complete(affectedRowsCount + "\n " + sql)
          }
        }
      )
    }~
    pathPrefix("update") {
      concat(
        pathEnd {
          complete("/item")
        },
        path(IntNumber) { int =>
          put {
            parameter("name", "price".as[Int]) { (name, price) =>
              val item = items.filter(_.id === int).map(i => (i.name, i.price))
              val action = item.update((name, price))
              val sql = item.updateStatement
              val affectedRowsCount: Future[Int] = db.run(action)

              complete(affectedRowsCount + "\n " + sql)
            }
          }
        }
      )
    }~
    path("items") {
      get {
        complete {
          val result = db.run(items.result).foreach(i => {
            i.foreach(println)
          })

          result.toString
        }
      }
    }~
    pathPrefix("item") {
      concat(
        pathEnd {
          complete("/item")
        },
        path(IntNumber) { int =>
          val result = db.run(items.filter(_.id === int).result).foreach(x => {
            x.foreach(println)
          })

          complete(result.toString)
        }
      )
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

}
