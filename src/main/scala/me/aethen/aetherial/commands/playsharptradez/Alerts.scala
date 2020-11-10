package me.aethen.aetherial.commands.playsharptradez

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import me.aethen.aetherial.commands.SubCommand
import cats.effect.IO
import doobie.Transactor
import doobie.implicits._
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

import scala.jdk.CollectionConverters.CollectionHasAsScala

object Alerts extends SubCommand {
  override def execute(event: MessageReceivedEvent, xa: Transactor[IO]): Unit = {
    // Run Database operation
    val result =
      sql"SELECT * FROM playsharp_exclusive_members"
        .query[Long]
        .to[Seq]
        .transact(xa)
        .unsafeRunSync()

    val eb = new EmbedBuilder()
      .setAuthor(
        event.getAuthor.getName,
        null,
        event.getAuthor.getAvatarUrl)
      .setThumbnail(event.getGuild.getIconUrl)
      .setImage(event.getMessage.getAttachments.asScala.headOption.map(_.getUrl).orNull)
      .setDescription("[Click here to go to the alert](" + event.getMessage.getJumpUrl + ")")
      .addField(event.getChannel.getName, event.getMessage.getContentRaw, false)
      .setFooter(LocalDateTime.now.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"))).build()

    result.foreach(event.getJDA.retrieveUserById(_)
      .queue(_.openPrivateChannel()
        .queue(_.sendMessage(eb)
          .queue())))
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {
    true
  }
}
