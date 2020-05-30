import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import Commands.Birthdays.SetBirthday

class AetherialListener(val prefix: String) extends ListenerAdapter {

  private val commands = SetBirthday.aliases.map(prefix + _ -> SetBirthday).toMap


  override def onMessageReceived(event: MessageReceivedEvent): Unit = {
    commands.get(event.getMessage.getContentRaw.split("\\s").head).foreach(_.execute(event))
  }
}
