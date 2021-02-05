package com.example.models

final case class Item(id: Int = 0, name: String, price: Int) {
  override def toString: String = s"{\n\t'id' = $id,\n\t'name' = '$name',\n\t'price' = $price\n}"
}

