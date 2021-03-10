package com.example.adapter

import akka.persistence.typed.{EventAdapter, EventSeq}
import com.example.models.Post.{CreatePostEvent, PostEvent, RegisterPostEvent}

class PostEventAdapter() extends EventAdapter[PostEvent, PostWrapper] {
  override def toJournal(e: PostEvent): PostWrapper = {
    val protoEvent = e match {
      case event: CreatePostEvent => {
        CreatePostEventV1(
          date = event.date.toString,
          postId = event.postId,
          name = event.name,
          address = event.address
        )
      }
      case event: RegisterPostEvent => {
        RegisterPostEventV1(
          date = event.date.toString,
          postId = event.postId
        )
      }
    }

    PostWrapper(protoEvent)
  }

  override def manifest(event: PostEvent): String = ""

  override def fromJournal(p: PostWrapper, manifest: String): EventSeq[PostEvent] = {
    p.event match {
      case event: CreatePostEventV1 => {
        EventSeq.single(
          CreatePostEvent(
            date = event.date.toLong,
            postId = event.postId,
            name = event.name,
            address = event.address
          )
        )
      }
  
      case event: RegisterPostEventV1 => {
        EventSeq.single(
          RegisterPostEvent(
            date = event.date.toLong,
            postId = event.postId
          )
        )
      }
    }
  }
}

case class PostWrapper(event: Product)

case class CreatePostEventV1(date: String, postId: String, name: String, address: String)

case class RegisterPostEventV1(date: String, postId: String)
