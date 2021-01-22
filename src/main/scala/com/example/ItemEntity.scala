package com.example

//import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, BSONObjectID}
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}
import spray.json.{DefaultJsonProtocol, JsValue, RootJsonFormat}

case class ItemEntity(id: BSONObjectID = BSONObjectID.generate, name: String, price: Int)

object ItemEntity {

  implicit def toItemEntity(item: Item) = ItemEntity(name = item.name, price = item.price)

  implicit object ItemEntityBSONReader extends BSONDocumentReader[ItemEntity] {
    def read(doc: BSONDocument): ItemEntity =
      ItemEntity(
        id = doc.getAs[BSONObjectID]("_id").get,
        name = doc.getAs[String]("name").get,
        price = doc.getAs[Int]("price").get
      )
  }

  implicit object ItemEntityBSONWriter extends BSONDocumentWriter[ItemEntity] {
    def write(itemEntity: ItemEntity): BSONDocument =
      BSONDocument(
        "_id" -> itemEntity.id,
        "name" -> itemEntity.name,
        "price" -> itemEntity.price
      )
  }
}

object ItemEntityProtocol extends DefaultJsonProtocol {
  implicit object BSONObjectIdProtocol extends RootJsonFormat[BSONObjectID] {
    override def read(json: JsValue): BSONObjectID = ???

    override def write(obj: BSONObjectID): JsValue = ???
  }

  implicit val EntityFormat = jsonFormat3(ItemEntity.apply)
}
