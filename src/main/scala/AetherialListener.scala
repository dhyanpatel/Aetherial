import java.util.NoSuchElementException
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import Commands.Birthdays.SetBirthday

class AetherialListener extends ListenerAdapter {

  private val commands = SetBirthday.aliases.map(_ -> SetBirthday).toMap

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {
    val msg = event.getMessage
    commands.get(msg.getContentRaw.split("\\s").head).foreach(_.execute(event))
  }
}
