package com.example.models

case class Item(id: Int, name: String, price: Int) {
  override def toString: String = s"{\n\t'id' = $id,\n\t'name' = '$name',\n\t'price' = $price\n}"
}

