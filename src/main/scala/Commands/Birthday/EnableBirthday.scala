package Commands.Birthday

import Commands.SubCommand
import cats.effect.{IO, Resource}
import doobie.hikari.HikariTransactor
import doobie.implicits._
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object EnableBirthday extends SubCommand {
  override def execute(event: MessageReceivedEvent, transactor: Resource[IO, HikariTransactor[IO]]): Unit = {
    // Command Validation
    if (!validateCommand(event))
      return

    val userId = event.getAuthor.getId
    val serverId = event.getGuild.getId

    // SQL Query
    val result = transactor.use(
      sql"SELECT fn_set_birthday_server(CAST($userId AS BIGINT), CAST($serverId AS BIGINT))"
        .query[Boolean]
        .unique
        .transact[IO]
    ).unsafeRunSync()

    if (result)
      event.getChannel.sendMessage("Birthday messages enabled on this server!").queue()
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {
    val message = event.getMessage.getContentRaw.split("\\s+")

    // Makes sure input doesn't have anything extra
    message.length == 2
  }
}
