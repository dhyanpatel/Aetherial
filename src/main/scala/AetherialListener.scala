import Commands.About.Help
import Commands.Birthday.Birthday
import Commands.Ping.Ping
import cats.effect.{IO, Resource}
import doobie.hikari.HikariTransactor
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class AetherialListener(val prefix: String, val transactor: Resource[IO, HikariTransactor[IO]])
  extends ListenerAdapter {

  private val commands = (Birthday.aliases.map(prefix + _ -> Birthday).toMap
    ++ Help.aliases.map(prefix + _ -> Help).toMap
    ++ Ping.aliases.map(prefix + _ -> Ping).toMap
    )

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {
    commands.get(event.getMessage.getContentRaw.split("\\s").head)
      .foreach(_.getSubCommand(event).execute(event, transactor))
  }
}