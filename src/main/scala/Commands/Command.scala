package Commands
import cats.effect.{IO, Resource}
import doobie.hikari.HikariTransactor
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

abstract class Command() {
  var aliases : Seq[String]
  var name : String
  def execute(event: MessageReceivedEvent, transactor: Resource[IO, HikariTransactor[IO]]) : Unit
  def validateCommand(event: MessageReceivedEvent) : Boolean
}
