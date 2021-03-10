package com.example.actors

import akka.Done
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.example.Database
import com.example.models.Item.{AddItemCommand, DeleteItemCommand, EditItemCommand, GetItemCommand, GetItemsCommand, ItemCommand}
import com.example.models.Summary

object ItemActor {
  def apply(database: Database): Behavior[ItemCommand] = {
    Behaviors.receive { (context, message) =>
      message match {

        case cmd: GetItemCommand =>
          println("Get " + cmd)
          cmd.replyTo ! Summary(database.getItem(cmd.id))
          Behaviors.same

        case cmd: GetItemsCommand =>
          println("Get all " + cmd)
          cmd.replyTo ! database.getItems
          Behaviors.same

        case cmd: AddItemCommand =>
          println("Add " + cmd)
          cmd.replyTo ! Summary(database.addItem(cmd.item))
          Behaviors.same

        case cmd: DeleteItemCommand =>
          println("Delete " + cmd)
          cmd.replyTo ! {
            if (database.deleteItem(cmd.id))
              Done
            else
//              throw Exception
              Done
          }
          Behaviors.same

        case cmd: EditItemCommand =>
          println("Update " + cmd)
          cmd.replyTo ! Summary(database.editItem(cmd.item))
          Behaviors.same

      }
    }
  }
}
