package com.example.models

import akka.Done
import akka.actor.typed.ActorRef

trait ItemCommand

case class AddItemCommand(item: Item, replyTo: ActorRef[Summary]) extends ItemCommand

case class EditItemCommand(item: Item, replyTo: ActorRef[Summary]) extends ItemCommand

case class DeleteItemCommand(id: Int, replyTo: ActorRef[Done]) extends ItemCommand
