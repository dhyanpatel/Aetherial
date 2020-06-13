package Commands
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

abstract class Command() {
  val aliases : Seq[String]
  val name : String
  def getSubCommand(event: MessageReceivedEvent) : SubCommand
}
