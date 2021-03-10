package com.example.models.Item

import akka.Done
import akka.actor.typed.ActorRef
import com.example.models.Summary

trait ItemCommand

case class GetItemCommand(id: Int, replyTo: ActorRef[Summary]) extends ItemCommand

case class GetItemsCommand(replyTo: ActorRef[Array[Item]]) extends ItemCommand

case class AddItemCommand(item: Item, replyTo: ActorRef[Summary]) extends ItemCommand

case class EditItemCommand(item: Item, replyTo: ActorRef[Summary]) extends ItemCommand

case class DeleteItemCommand(id: Int, replyTo: ActorRef[Done]) extends ItemCommand
