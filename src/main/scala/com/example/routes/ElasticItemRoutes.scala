package com.example.routes

import akka.Done
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.AskPattern._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.example.models.Item.{AddItemCommand, DeleteItemCommand, EditItemCommand, GetItemCommand, GetItemsCommand, Item, ItemCommand}
import com.example.models._
import com.typesafe.config.Config
import com.example.util.Codec
import com.sksamuel.elastic4s.ElasticClient
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class ElasticItemRoutes(elasticSearchClient: ElasticClient, itemActor: ActorSystem[ItemCommand])(implicit system: ActorSystem[_],
                                                            implicit val executionContext: ExecutionContext,
                                                            config: Config) extends Codec with FailFastCirceSupport {
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
