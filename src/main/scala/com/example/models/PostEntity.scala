package com.example.models

import akka.actor.typed.Behavior
import akka.cluster.sharding.typed.scaladsl.EntityTypeKey
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{Effect, EventSourcedBehavior}

object PostEntity {

  case class Post(date: Option[Long] = None,
                  name: Option[String] = None,
                  postId: Option[String] = None,
                  address: Option[String] = None)


  object Post {
    def empty = new Post()
  }


  trait State

  trait PostEntityState

  object PostEntityState {

    case object REGISTER extends PostEntityState

    case object INIT extends PostEntityState

    case object SEND extends PostEntityState

    case object FINISH extends PostEntityState

    case object CLOSE extends PostEntityState

  }

  case class StateHolder(content: Post, state: PostEntityState) {

    def update(event: PostEvent): StateHolder = event match {
      case event: CreatePostEvent => {
        copy(
          content = content.copy(
            date = Some(event.date),
            name = Some(event.name),
            postId = Some(event.postId),
            address = Some(event.address)
          ),
          state = PostEntityState.REGISTER
        )
      }

      case event: RegisterPostEvent => {
        copy(
          content = content.copy(
            date = Some(event.date),
            postId = Some(event.postId)
          ),
          state = PostEntityState.SEND
        )
      }
    }
  }

  object StateHolder {
    def empty: StateHolder = StateHolder(content = Post.empty, state = PostEntityState.INIT)
  }


  val EntityKey: EntityTypeKey[PostCommand] = EntityTypeKey[PostCommand]("Post")


  def apply(postId: String): Behavior[PostCommand] = {
    EventSourcedBehavior[PostCommand, PostEvent, StateHolder](
      persistenceId = PersistenceId(EntityKey.name, postId),
      StateHolder.empty,
      (state, command) => commandHandler(postId, state, command),
      (state, event) => handleEvent(state, event)
    )
  }


  def commandHandler(postId: String, state: StateHolder, command: PostCommand): Effect[PostEvent, StateHolder] = {
    command match {
      case command: CreatePostCommand => {
        state.state match {
          case PostEntityState.INIT => {

            val event = CreatePostEvent(
              date = command.date,
              postId = command.postId,
              name = command.name,
              address = command.address
            )

            Effect.persist(event)
          }

          case _ => throw new RuntimeException("Error")
        }
      }

      case command: RegisterPostCommand => {
        state.state match {
          case PostEntityState.REGISTER => {

            val event = RegisterPostEvent(
              date = command.date,
              postId = command.postId
            )

            Effect.persist(event)
          }

        }
      }
    }
  }

  def handleEvent(state: StateHolder, event: PostEvent): StateHolder = {
    state.update(event)
  }

}