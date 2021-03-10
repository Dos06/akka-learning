package com.example.repositories

import com.example.models.Item.Item
import com.example.util.Codec
import com.sksamuel.elastic4s.requests.delete.DeleteResponse
import com.sksamuel.elastic4s.requests.mappings.{KeywordField, MappingDefinition}
import com.sksamuel.elastic4s.{ElasticClient, Response}
import com.typesafe.config.Config
import io.circe.parser._
import io.circe.syntax._

import scala.concurrent.{ExecutionContext, Future}

class ItemElasticRepository(val elasticSearchClient: ElasticClient)
                                     (implicit val ec: ExecutionContext,
                                      implicit val config: Config) extends ElasticSearchRepository[Item] with Codec {

  implicit val manifest: Manifest[Item] = Manifest.classType[Item](classOf[Item])

  override def encode: Item => String = (entity: Item) => {
    entity.asJson.noSpaces
  }

  override def decode: String => Item = (jsonString: String) => {
    parse(jsonString).toTry.get.as[Item].toTry.get
  }

  override def indexName: String = config.getString(s"elastic.dmsUserTasks")

  override def shards: Int = config.getInt("elastic.shards")

  override def replicas: Int = config.getInt("elastic.replicas")


  def deleteById(id: String): Future[String] = {
    val response: Future[Response[DeleteResponse]] = elasticSearchClient.execute {
      deleteById(indexName, id)
    }
    response.map { r =>
      if (r.status == 200) {
        r.result.id
      } else {
        throw r.error.asException
      }
    }
  }

  override def mapping: Option[MappingDefinition] = Some(properties(
    KeywordField("id"),
    KeywordField("name"),
    KeywordField("price")
  ))

}