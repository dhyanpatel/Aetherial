package me.aethen.aetherial.commands.ping

import me.aethen.aetherial.commands.{Command, SubCommand}
import cats.effect.IO
import doobie.Transactor
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object Ping extends Command with SubCommand {
  override val aliases: Seq[String] = Seq("ping")
  override val name: String = "Ping"

  override def getSubCommand(event: MessageReceivedEvent): SubCommand = {
    Ping
  }

  override def execute(event: MessageReceivedEvent, xa: Transactor[IO]): Unit = {
    event.getChannel.sendMessage("Pong").queue()
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {
    true
  }
}