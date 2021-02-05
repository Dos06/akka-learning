package com.example

import com.example.models.{Item, ItemTable}
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global

object BootSlick extends App {
  val db = Database.forConfig("mysql")
  lazy val items = TableQuery[ItemTable]

  db.run(items.schema.createIfNotExists)
//  db.run(items += Item(0, "Ahahah", 333))
//  db.run(items += Item(10, "))))", 999))

  db.run(items.result).foreach(i => {
    i.foreach(println)
  })

}
