package me.aethen.aetherial

import cats.effect.IO
import doobie.Transactor
import me.aethen.aetherial.commands.birthday.Birthday
import me.aethen.aetherial.commands.ping.Ping
import me.aethen.aetherial.commands.playsharptradez.Alerts
import me.aethen.aetherial.commands.about.Help
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class AetherialListener(val prefix: String, val xa: Transactor[IO])
  extends ListenerAdapter {

  private val commands = (Birthday.aliases.map(prefix + _ -> Birthday).toMap
    ++ Help.aliases.map(prefix + _ -> Help).toMap
    ++ Ping.aliases.map(prefix + _ -> Ping).toMap
    )

  private val playsharpPremiumChannels = Seq(750496901803147364L, 744332554366353501L, 750496942810857552L, 747518698713252012L)

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {

    if (event.getAuthor.isBot) {
      return
    }
    // Functionality for PlaySharpTradez Discord Server
    if (event.getGuild.getIdLong == 705206774239723531L) {
      if (event.getMessage.getContentRaw.contains("discord.gg")) {
        event.getChannel
          .sendMessage(s"Sorry ${event.getAuthor.getAsTag}, you can't link other Discord Servers here!")
          .flatMap(_ => event.getMessage.delete())
          .queue()
      }
      if(event.getMessage.getContentRaw.contains("uni-airdrop")){
        event.getGuild.ban(event.getAuthor, 1, "Spam").queue()
        event.getChannel.sendMessage(s"User ${event.getAuthor.getName} has been banned for spamming").queue()
      }
    }
    if (playsharpPremiumChannels.contains(event.getChannel.getIdLong)) {
      Alerts.execute(event, xa)
    }

    commands.get(event.getMessage.getContentRaw.split("\\s").head.toLowerCase)
      .foreach(_.getSubCommand(event).execute(event, xa))
  }
}