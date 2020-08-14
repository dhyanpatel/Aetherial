package Commands.Birthday

import Commands.{Command, SubCommand}
import cats.effect.{IO, Resource}
import doobie.Transactor
import doobie.hikari.HikariTransactor
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object Birthday extends Command with SubCommand {

  override val aliases: Seq[String] = Seq("birthday")
  override val name: String = "Birthday"

  override def getSubCommand(event: MessageReceivedEvent): SubCommand = {
    event.getMessage.getContentRaw.split("\\s+").lift(1) match {
      case None => Birthday
      case Some(value) => value match {
        case "set" => SetBirthday
        case "enable" => EnableBirthday
        case _ => Birthday
      }
    }
  }

  override def execute(event: MessageReceivedEvent, xa: Transactor[IO]): Unit = {
    event.getChannel.sendMessage("Here are a few subcommands for Birthday:\n" +
      "set - Set your birthday\n" +
      "remove - Remove your birthday\n" +
      "enable - Enable birthday messages in this server\n" +
      "disable - Disable birthday messages in this server\n" +
      "channel - For Moderators only, allow birthday messages in this channel").queue()
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {
    true
  }
}
