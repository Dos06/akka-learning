package com.example.actors

import akka.Done
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.example.Database
import com.example.models.{AddItemCommand, DeleteItemCommand, EditItemCommand, Summary, ItemCommand}

object ItemActor {
  def apply(database: Database): Behavior[ItemCommand] = {
    Behaviors.receive { (context, message) =>
      message match {
        case cmd: AddItemCommand =>
          println("Add " + cmd)
          cmd.replyTo ! Summary(database.addItem(cmd.item))
          Behaviors.same

        //        case cmd: EditItemCommand =>
        //          println("Edit " + cmd)
        //          cmd.replyTo ! Summary(database.editItem(cmd.item))
        //          Behaviors.same

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
