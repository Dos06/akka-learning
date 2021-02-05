package com.example.models

import slick.jdbc.MySQLProfile.api._

final class ItemTable(tag: Tag) extends Table[Item](tag, "items") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def price = column[Int]("price")

  override def * = (id, name, price).mapTo[Item]
}