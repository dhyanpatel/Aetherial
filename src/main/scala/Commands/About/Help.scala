package Commands.About

import Commands.{Command, SubCommand}
import cats.effect.IO
import doobie.Transactor
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object Help extends Command with SubCommand {
  override val aliases: Seq[String] = Seq("help")
  override val name: String = "Help"

  override def getSubCommand(event: MessageReceivedEvent): SubCommand = {
    Help
  }

  override def execute(event: MessageReceivedEvent, xa: Transactor[IO]): Unit = {
    event.getChannel.sendMessage(
      "Available Commands: \n" +
        "Birthday commands - Type !birthday to view available birthday commands \n").queue()
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {
    true
  }
}