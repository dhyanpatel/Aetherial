package Commands
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

abstract class Command() {
  var aliases : Seq[String]
  var name : String
  def execute(event: MessageReceivedEvent)
}
