package com.example

import com.example.models.Item
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

class Database {
  private val hikariConfig = new HikariConfig()

  private val ds = {
    hikariConfig.setDataSourceClassName(null)
    hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver")

    hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/spring_blog")
    hikariConfig.setUsername("root")
    hikariConfig.setPassword("")

    new HikariDataSource(hikariConfig)
  }

  def getConnection() = {
    ds.getConnection()
  }

  def addItem(item: Item): Item = {
    val sql = "INSERT INTO items (name, price) VALUES (?, ?)"
    val conn = getConnection()
    val pst = conn.prepareStatement(sql)

    pst.setString(1, item.name)
    pst.setInt(2, item.price)

    pst.execute()
    conn.close()

    item
  }

  def getItem(itemId: Int): Item = {
    val sql = "SELECT name, price FROM items WHERE id = ?"

    val conn = getConnection()
    val pst = conn.prepareStatement(sql)
    pst.setInt(1, itemId)

    val rs = pst.executeQuery()
    var item = Item(0, "", 0)

    while (rs.next()) {
      item = Item(itemId, rs.getString("name"), rs.getInt("price"))
    }
    conn.close()

    item
  }

  def getItems: Array[Item] = {
    val sql = "SELECT id, name, price FROM items"

    var id = 0
    var name: String = ""
    var price: Int = 0
    val array = Array[Item]()

    val conn = getConnection()
    val pst = conn.prepareStatement(sql)

    val rs = pst.executeQuery()

    while (rs.next()) {
      id = rs.getInt("id")
      name = rs.getString("name")
      price = rs.getInt("price")
      val item = Item(id, name, price)
      array :+ item
      println(item)
    }

    conn.close()

    array
  }

  def deleteItem(id: Int): Boolean = {
    val sql = "DELETE FROM items WHERE id = ?"
    val conn = getConnection()
    val pst = conn.prepareStatement(sql)

    pst.setInt(1, id)

    val temp = pst.executeUpdate()
    conn.close()

    temp > 0
  }

  def editItem(item: Item): Item = {
    val sql = "UPDATE items SET name = ?, price = ? WHERE id = ?"
    val conn = getConnection()
    val pst = conn.prepareStatement(sql)

    pst.setString(1, item.name)
    pst.setInt(2, item.price)
    pst.setInt(3, item.id)

    pst.executeUpdate()
    conn.close()

    item
  }
}
