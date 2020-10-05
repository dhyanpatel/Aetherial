import Commands.About.Help
import Commands.Birthday.Birthday
import Commands.Ping.Ping
import Commands.PlaySharpTradez.Alerts
import cats.effect.IO
import doobie.Transactor
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class AetherialListener(val prefix: String, val xa: Transactor[IO])
  extends ListenerAdapter {

  private val commands = (Birthday.aliases.map(prefix + _ -> Birthday).toMap
    ++ Help.aliases.map(prefix + _ -> Help).toMap
    ++ Ping.aliases.map(prefix + _ -> Ping).toMap
    )

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {

    if(event.getAuthor.isBot){
      return
    }
    // Functionality for PlaySharpTradez Discord Server
    if(event.getGuild.getIdLong.equals(705206774239723531L)){
      if(event.getMessage.getContentRaw.contains("discord.gg")){
        event.getMessage.delete().queue()
        event.getChannel.sendMessage(s"Sorry ${event.getAuthor.getAsTag}, you can't link other Discord Servers here!").queue()
      }
    }
//    if(Seq(721867084979634216L).contains(event.getChannel.getIdLong)) {
    if(Seq(750496901803147364L, 744332554366353501L, 750496942810857552L, 747518698713252012L).contains(event.getChannel.getIdLong)){
      Alerts.execute(event, xa)
  }

    commands.get(event.getMessage.getContentRaw.split("\\s").head)
      .foreach(_.getSubCommand(event).execute(event, xa))
  }
}