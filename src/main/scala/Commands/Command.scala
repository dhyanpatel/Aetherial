package Commands
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

abstract class Command() {
  def execute(event: MessageReceivedEvent)
}
