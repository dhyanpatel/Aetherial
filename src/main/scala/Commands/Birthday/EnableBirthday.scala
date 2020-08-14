package Commands.Birthday

import Commands.SubCommand
import cats.effect.IO
import doobie.Transactor
import doobie.implicits._
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object EnableBirthday extends SubCommand {
  override def execute(event: MessageReceivedEvent, xa: Transactor[IO]): Unit = {
    // Command Validation
    if (!validateCommand(event))
      return

    val userId = event.getAuthor.getId
    val serverId = event.getGuild.getId

    // SQL Query
    val result =
      sql"SELECT fn_set_birthday_server(CAST($userId AS BIGINT), CAST($serverId AS BIGINT))"
        .query[Boolean]
        .unique
        .transact(xa)
        .unsafeRunSync()

    if (result)
      event.getChannel.sendMessage("Birthday messages enabled on this server!").queue()
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {
    val message = event.getMessage.getContentRaw.split("\\s+")

    // Makes sure input doesn't have anything extra
    message.length == 2
  }
}
