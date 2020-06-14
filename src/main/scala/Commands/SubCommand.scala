package Commands

import cats.effect.{IO, Resource}
import doobie.hikari.HikariTransactor
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

trait SubCommand {
  def execute(event: MessageReceivedEvent, transactor: Resource[IO, HikariTransactor[IO]]): Unit

  def validateCommand(event: MessageReceivedEvent): Boolean
}
