package com.example.models.Post

trait PostCommand {
  def date: Long

  def postId: String
}

case class CreatePostCommand(date: Long,
                             postId: String,
                             name: String,
                             address: String) extends PostCommand

case class RegisterPostCommand(date: Long,
                               postId: String) extends PostCommand

case class SendPostCommand(date: Long,
                           postId: String) extends PostCommand

case class ResendPostCommand(date: Long,
                             postId: String) extends PostCommand

case class ReceivePostCommand(date: Long,
                              postId: String) extends PostCommand

case class LosePostCommand(date: Long,
                           postId: String) extends PostCommand

case class ReturnPostCommand(date: Long,
                             postId: String) extends PostCommand

case class AcceptBackPostCommand(date: Long,
                                 postId: String) extends PostCommand
