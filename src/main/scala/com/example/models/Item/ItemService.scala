package com.example.models.Item

import akka.actor.typed.ActorSystem
import com.example.repositories.ItemElasticRepository
import com.sksamuel.elastic4s.ElasticClient
import com.typesafe.config.Config

import scala.concurrent.{ExecutionContext, Future}

class ItemService (elasticSearchClient: ElasticClient)(implicit system: ActorSystem[_],
                                                                 implicit val executionContext: ExecutionContext,
                                                                 config: Config) {

  implicit val itemElasticRepository: ItemElasticRepository = new ItemElasticRepository(elasticSearchClient)

  def create(item: Item): Future[Item] = {
    itemElasticRepository.createIndexIfNotExists()
    itemElasticRepository.upsert(item)
  }

  def findPostById(id: String): Future[Option[Item]] = {
    itemElasticRepository.find(id)
  }

  def edit(item: Item): Future[Item] = {
    itemElasticRepository.upsert(item)
  }

  def delete(id: String): Future[String] = {
    itemElasticRepository.deleteById(id)
  }

}
