package Commands.Birthday

import Commands.SubCommand
import cats.effect.{IO, Resource}
import doobie.hikari.HikariTransactor
import doobie.implicits._
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object SetBirthday extends SubCommand {
  override def execute(event: MessageReceivedEvent, transactor: Resource[IO, HikariTransactor[IO]]): Unit = {
    // Exit if invalid command
    if (!validateCommand(event))
      return

    val userId = event.getAuthor.getId
    val date = event.getMessage.getContentRaw.split(" ").last
    // If DB returned true
    // Run Database operation
    val result = transactor.use(
      sql"SELECT fn_set_user_birthday(CAST($userId AS BIGINT), CAST($date AS DATE))"
        .query[Boolean]
        .unique
        .transact[IO]
    ).unsafeRunSync()

    if (result)
      event.getChannel.sendMessage("Birthday Successfully Added!").queue()
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {
    val message = event.getMessage.getContentRaw.split("\\s+")

    // Only 1 parameter required (birth date)
    // Birth date must be dd-mm-yyyy format
    message.length == 3 && message.last.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d")
  }
}
