import java.util.NoSuchElementException
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import Commands.Birthdays.SetBirthday

class AetherialListener extends ListenerAdapter {

  private var commands = Map("!setBirthday" -> SetBirthday)

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {
    val msg = event.getMessage
    try {
      commands(msg.getContentRaw.split(' ')(0)).execute(event)
    } catch {
      case x: NoSuchElementException =>
    }
  }

}
