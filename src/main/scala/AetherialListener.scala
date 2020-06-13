import Commands.Birthdays.SetBirthday
import cats.effect.{IO, Resource}
import doobie.hikari.HikariTransactor
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class AetherialListener(val prefix: String, val transactor: Resource[IO, HikariTransactor[IO]])
  extends ListenerAdapter {

  private val commands = SetBirthday.aliases.map(prefix + _ -> SetBirthday).toMap

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {
    commands.get(event.getMessage.getContentRaw.split("\\s").head).foreach(_.execute(event, transactor))
  }
}