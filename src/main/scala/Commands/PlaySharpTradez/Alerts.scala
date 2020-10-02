package Commands.PlaySharpTradez

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import Commands.SubCommand
import cats.effect.IO
import doobie.Transactor
import doobie.implicits._
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

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
      .setDescription("[Click here to go to the alert]("+event.getMessage.getJumpUrl+")")
      .addField(event.getChannel.getName, event.getMessage.getContentRaw, false)
      .setFooter(LocalDateTime.now.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")))

    result.foreach(user => {
      event.getJDA.getUserById(user).openPrivateChannel().queue(channel => {
        channel.sendMessage(eb.build).queue()
      })
    })
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {
    true
  }
}
