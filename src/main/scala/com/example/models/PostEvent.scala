package com.example.models

trait PostEvent

case class CreatePostEvent(date: Long,
                           postId: String,
                           name: String,
                           address: String) extends PostEvent

case class RegisterPostEvent(date: Long,
                             postId: String) extends PostEvent

case class SendPostEvent(date: Long,
                         postId: String) extends PostEvent

case class ReceivePostEvent(date: Long,
                            postId: String) extends PostEvent
