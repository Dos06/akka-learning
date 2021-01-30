package com.example.routes

import akka.Done
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.Directives.{entity, _}
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import com.example.models.{AddItemCommand, DeleteItemCommand, EditItemCommand, GetItemCommand, GetItemsCommand, Item, ItemCommand, Summary}
import com.example.util.Codec
import akka.actor.typed.scaladsl.AskPattern._
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._

class ItemRoutes(itemActor: ActorSystem[ItemCommand])(implicit system: ActorSystem[_]) extends Codec with FailFastCirceSupport {

  implicit val timeout: Timeout = 3.seconds

  val routes: Route = {
    itemAddRoute ~ itemEditRoute ~ itemGetRoute ~ itemDeleteRoute ~ itemsGetRoute
  }

  def itemGetRoute(): Route = {
    pathPrefix("item") {
      concat(
        pathEnd {
          complete("/item")
        },
        path(IntNumber) { id =>
          val reply: Future[Summary] = itemActor.ask(GetItemCommand(id, _))

          onComplete(reply) { summary =>
            complete(summary)
          }
        }
      )
    }
  }

  def itemsGetRoute(): Route = {
    pathPrefix("items") {
      get {
        val reply: Future[Array[Item]] = itemActor.ask(GetItemsCommand)

        onComplete(reply) { summary =>
          complete(summary)
        }

      }
    }
  }

  def itemAddRoute(): Route = {
    pathPrefix("item" / "add") {
      post {
//        entity(as[Item]) { entity =>
//          val reply: Future[Summary] = itemActor.ask(AddItemCommand(entity, _))
//
//          onComplete(reply) { summary =>
//            complete(summary)
//          }
//
//        }

        parameter("name", "price".as[Int]) { (name, price) =>
          val item = Item(0, name, price)
          val reply: Future[Summary] = itemActor.ask(AddItemCommand(item, _))

          onComplete(reply) { summary =>
            complete(summary)
          }
        }

      }
    }
  }

  def itemEditRoute(): Route = {
    pathPrefix("item" / "edit") {
      put {
//        entity(as[Item]) { entity =>
//          val reply: Future[Summary] = itemActor.ask(EditItemCommand(entity, _))
//
//          onComplete(reply) { summary =>
//            complete(summary)
//          }
//
//        }

        parameter("id".as[Int], "name", "price".as[Int]) { (id, name, price) =>
          val item = Item(id, name, price)
          val reply: Future[Summary] = itemActor.ask(EditItemCommand(item, _))

          onComplete(reply) { summary =>
            complete(summary)
          }
        }

      }
    }
  }

  def itemDeleteRoute(): Route = {
    pathPrefix("item" / "delete") {
      delete {
        parameter("id".as[Int]) { id =>
          val reply: Future[Done] = itemActor.ask(DeleteItemCommand(id, _))

          onComplete(reply) { summary =>
            complete(summary)
          }

        }
      }
    }
  }

}
