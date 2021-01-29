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

    var name: String = ""
    var price: Int = 0

    val conn = getConnection()
    val pst = conn.prepareStatement(sql)
    pst.setInt(1, itemId)

    val rs = pst.executeQuery()
    conn.close()

    while (rs.next()) {
      name = rs.getString("name")
      price = rs.getInt("price")
    }

    Item(itemId, name, price)
  }

  def getItems(itemId: Int): Array[Item] = {
    val sql = "SELECT name, price FROM items"

    var name: String = ""
    var price: Int = 0
    val array = Array[Item]()

    val conn = getConnection()
    val pst = conn.prepareStatement(sql)
    pst.setInt(1, itemId)

    val rs = pst.executeQuery()
    conn.close()

    while (rs.next()) {
      name = rs.getString("name")
      price = rs.getInt("price")
      array :+ Item(itemId, name, price)
    }

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
