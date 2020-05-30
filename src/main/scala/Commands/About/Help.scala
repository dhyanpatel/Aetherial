package Commands.About

import Commands.Command
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object Help extends Command {
  override var aliases: Seq[String] = Seq("help")
  override var name: String = "help"

  override def execute(event: MessageReceivedEvent): Unit = {
    val channel = event.getChannel
    channel.sendMessage("Current features: \n Add Birthday (addBirthday) \n More commands soon!")
      .queue()
  }
}
