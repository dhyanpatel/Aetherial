package Commands

import cats.effect.IO
import doobie.Transactor
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

trait SubCommand {
  def execute(event: MessageReceivedEvent, xa: Transactor[IO]): Unit

  def validateCommand(event: MessageReceivedEvent): Boolean
}
