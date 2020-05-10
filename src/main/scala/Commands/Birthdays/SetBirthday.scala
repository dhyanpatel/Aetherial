package Commands.Birthdays
import Commands.Command
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object SetBirthday extends Command{
  override def execute(event: MessageReceivedEvent): Unit = {
    val channel = event.getChannel
    channel.sendMessage("This is when I would add your birthday to my database. I have not implemented this yet, but it" +
      " is in the works!")
      .queue()
  }
}
