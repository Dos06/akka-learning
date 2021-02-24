package com.example.models

import akka.actor.typed.Behavior
import akka.cluster.sharding.typed.scaladsl.EntityTypeKey
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{Effect, EventSourcedBehavior, RetentionCriteria}
import com.example.adapter.PostEventAdapter

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

    case object INIT extends PostEntityState
    case object REGISTER extends PostEntityState
    case object SEND extends PostEntityState
    case object RESEND extends PostEntityState
    case object FINISH extends PostEntityState
    case object CLOSE extends PostEntityState
    case object LOST extends PostEntityState
    case object RETURNED extends PostEntityState
    case object ACCEPTED_BACK extends PostEntityState

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

      case event: SendPostEvent => {
        copy(
          content = content.copy(
            date = Some(event.date),
            postId = Some(event.postId)
          ),
          state = PostEntityState.FINISH
        )
      }

      case event: ResendPostEvent => {
        copy(
          content = content.copy(
            date = Some(event.date),
            postId = Some(event.postId)
          ),
          state = PostEntityState.RESEND
        )
      }

      case event: ReceivePostEvent => {
        copy(
          content = content.copy(
            date = Some(event.date),
            postId = Some(event.postId)
          ),
          state = PostEntityState.CLOSE
        )
      }

      case event: LosePostEvent => {
        copy(
          content = content.copy(
            date = Some(event.date),
            postId = Some(event.postId)
          ),
          state = PostEntityState.FINISH
        )
      }

      case event: ReturnPostEvent => {
        copy(
          content = content.copy(
            date = Some(event.date),
            postId = Some(event.postId)
          ),
          state = PostEntityState.RESEND
        )
      }

      case event: AcceptBackPostEvent => {
        copy(
          content = content.copy(
            date = Some(event.date),
            postId = Some(event.postId)
          ),
          state = PostEntityState.CLOSE
        )
      }
    }
  }

  object StateHolder {
    def empty: StateHolder = StateHolder(content = Post.empty, state = PostEntityState.INIT)
  }


  val EntityKey: EntityTypeKey[PostCommand] = EntityTypeKey[PostCommand]("Post")

  def apply(postId: String, eventProcessorTag: Set[String]): Behavior[PostCommand] = {
    EventSourcedBehavior[PostCommand, PostEvent, StateHolder](
      persistenceId = PersistenceId(EntityKey.name, postId),
      StateHolder.empty,
      (state, command) => commandHandler(postId, state, command),
      (state, event) => handleEvent(state, event)
    ).withTagger(_ => eventProcessorTag)
      .withRetention(RetentionCriteria.snapshotEvery(numberOfEvents = 10, keepNSnapshots = 2))
      .eventAdapter(new PostEventAdapter)
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

      case command: SendPostCommand => {
        state.state match {
          case PostEntityState.SEND => {
            val event = SendPostEvent(
              date = command.date,
              postId = command.postId
            )
            Effect.persist(event)
          }
        }
      }

      case command: ResendPostCommand => {
        state.state match {
          case PostEntityState.RESEND => {
            val event = ResendPostEvent(
              date = command.date,
              postId = command.postId
            )
            Effect.persist(event)
          }
        }
      }

      case command: ReceivePostCommand => {
        state.state match {
          case PostEntityState.FINISH => {
            val event = ReceivePostEvent(
              date = command.date,
              postId = command.postId
            )
            Effect.persist(event)
          }
        }
      }

      case command: LosePostCommand => {
        state.state match {
          case PostEntityState.LOST => {
            val event = SendPostEvent(
              date = command.date,
              postId = command.postId
            )
            Effect.persist(event)
          }
        }
      }

      case command: ReturnPostCommand => {
        state.state match {
          case PostEntityState.RETURNED => {
            val event = ResendPostEvent(
              date = command.date,
              postId = command.postId
            )
            Effect.persist(event)
          }
        }
      }

      case command: AcceptBackPostCommand => {
        state.state match {
          case PostEntityState.ACCEPTED_BACK => {
            val event = ReceivePostEvent(
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