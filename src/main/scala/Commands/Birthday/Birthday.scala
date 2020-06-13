package Commands.Birthday

import Commands.{Command, SubCommand}
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object Birthday extends Command {

  override val aliases: Seq[String] = Seq("birthday")
  override val name: String = "Birthday"

  override def getSubCommand(event: MessageReceivedEvent): SubCommand = {
    event.getMessage.getContentRaw.split("\\s")(1) match {
      case "set" => SetBirthday
    }
  }
}
