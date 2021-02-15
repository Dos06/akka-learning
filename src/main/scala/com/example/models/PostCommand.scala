package com.example.models

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

case class ReceivePostCommand(date: Long,
                              postId: String) extends PostCommand
