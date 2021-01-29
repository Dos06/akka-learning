//package com.example
//
//import akka.Done
//import akka.actor.{ActorRef, ActorSystem}
//import akka.stream.ActorMaterializer
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model.StatusCodes.OK
//import akka.http.scaladsl.server.Directives._
//
//import scala.concurrent.Future
//
//object Application extends App {
//  implicit val system = ActorSystem("A-system")
//  implicit val materializer = ActorMaterializer
//  implicit val ec = system.dispatcher
//
////  var item1 = Item(1, "i1", 100)
//  var item2 = Item(2, "i2", 200)
//  var item3 = Item(3, "i3", 300)
//
//  var arr = Array(item1, item2, item3)
////  arr = arr.patch(1, Nil, 1)
//
//  val route =
//    path("") {
//      get {
//        complete("Home page")
//      }
//    }~
//    path("item") {
//      post {
////        parameter("id".as[Int], "name", "price".as[Int]) { (id, name, price) =>
////          arr = arr :+ Item(id, name, price)
////          complete("Item created")
////        }
//
//        entity(as[Item]) { entity =>
//          val reply: Future[Summary] = itemA
//        }
//      }
//    }~
//    pathPrefix("delete") {
//      concat(
//        pathEnd {
//          complete("/item")
//        },
//        path(IntNumber) { int =>
//          delete {
//            var index: Int = -1
//            var counter = 0
//            for (v <- arr) {
//              if (v.id == int) index = counter
//              counter += 1
//            }
//            if (index >= 0) {
//              arr = arr.patch(index, Nil, 1)
//              complete("Item deleted")
//            }
//            else complete("Not found")
//          }
//        }
//      )
//    }~
//    pathPrefix("update") {
//      concat(
//        pathEnd {
//          complete("/item")
//        },
//        path(IntNumber) { int =>
//          put {
//            parameter("name", "price".as[Int]) { (name, price) =>
//              var index: Int = -1
//              var counter = 0
//              for (v <- arr) {
//                if (v.id == int) index = counter
//                counter += 1
//              }
//              if (index >= 0) {
//                arr(index).name = name
//                arr(index).price = price
//                complete("Item updated")
//              }
//              else complete("Not found")
//            }
//          }
//        }
//      )
//    }~
//    path("items") {
//      get {
//        complete {
//          var str = ""
////          val array: Array[Item] = getItems()
//          for (v <- arr) {
//            str += v.toString + "\n"
//          }
//          str
//        }
//      }
//    }~
//    pathPrefix("item") {
//      concat(
//        pathEnd {
//          complete("/item")
//        },
//        path(IntNumber) { int =>
//          var index = -1
//          var counter = 0
//          for (v <- arr) {
//            if (v.id == int) index = counter
//            counter += 1
//          }
//          if (index >= 0) complete(arr(index).toString)
//          else complete("Not found")
//        }
//      )
//    }
//
//  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
//}
//
////case class Item(var id: Int, var name: String, var price: Int) {
////  override def toString: String = s"{\n\t'id' = $id,\n\t'name' = '$name',\n\t'price' = $price\n}"
////}
//
////case class Summary(item: Item)
//
////trait ItemCommand
////
////case class AddItemCommand(item: Item, replyTo: ActorRef[Summary]) extends ItemCommand
////case class EditItemCommand(item: Item, replyTo: ActorRef[Summary]) extends ItemCommand
////case class DeleteItemCommand(item: Item, replyTo: ActorRef[Done]) extends ItemCommand
