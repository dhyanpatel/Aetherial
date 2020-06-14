package Commands.About

import Commands.{Command, SubCommand}
import cats.effect.{IO, Resource}
import doobie.hikari.HikariTransactor
import doobie.util.transactor.Transactor
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object Help extends Command with SubCommand{
  override val aliases: Seq[String] = Seq("help")
  override val name: String = "Help"

  override def getSubCommand(event: MessageReceivedEvent): SubCommand = {
    event.getMessage.getContentRaw.split("\\s+").lift(1) match{
      case None => Help
    }
  }

  override def execute(event: MessageReceivedEvent, transactor: Resource[IO, HikariTransactor[IO]]): Unit = {
    event.getChannel.sendMessage(
      "Available Commands: \n" +
      "Birthday commands - Type .birthday to view available birthday commands \n").queue()
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {
    true
  }
}