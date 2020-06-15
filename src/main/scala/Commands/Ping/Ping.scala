package Commands.Ping

import Commands.{Command, SubCommand}
import cats.effect.{IO, Resource}
import doobie.hikari.HikariTransactor
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object Ping extends Command with SubCommand {
  override val aliases: Seq[String] = Seq("ping")
  override val name: String = "Ping"

  override def getSubCommand(event: MessageReceivedEvent): SubCommand = {
    Ping
  }

  override def execute(event: MessageReceivedEvent, transactor: Resource[IO, HikariTransactor[IO]]): Unit = {
    event.getChannel.sendMessage("Pong").queue()
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {
    true
  }
}