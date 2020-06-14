package Commands.Birthday

import Commands.SubCommand
import cats.effect.{IO, Resource}
import doobie.hikari.HikariTransactor
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object EnableBirthday extends SubCommand {
  override def execute(event: MessageReceivedEvent, transactor: Resource[IO, HikariTransactor[IO]]): Unit = {
    if(!validateCommand(event))
      return
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {
    val message = event.getMessage.getContentRaw.split("\\s+")

    message.length == 2
  }
}
