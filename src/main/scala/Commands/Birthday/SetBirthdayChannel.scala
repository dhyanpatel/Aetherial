package Commands.Birthday
import Commands.SubCommand
import cats.effect.{IO, Resource}
import doobie.hikari.HikariTransactor
import doobie.implicits._
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent


object SetBirthdayChannel extends SubCommand {
  override def execute(event: MessageReceivedEvent, transactor: Resource[IO, HikariTransactor[IO]]): Unit = {
    if(!validateCommand(event)){
      return
    }

    val serverId = event.getGuild.getId
    val channelId = event.getChannel.getId

    val result = transactor.use(
      sql"SELECT fn_set_birthday_channel(CAST($serverId as BIGINT), CAST($channelId as BIGINT))"
        .query[Boolean]
        .unique
        .transact[IO]
    ).unsafeRunSync()

    if(result){
      event.getChannel.sendMessage("The bot will post birthday announcements on this channel").queue()
    }
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {

    if(event.getMember.hasPermission(Permission.ADMINISTRATOR)){
      val message = event.getMessage.getContentRaw.split("\\s+")
      if(message.length == 2){
        true
      }
      else{
        event.getChannel.sendMessage("Make sure no extra text is included after 'channel' in the command").queue()
        false
      }
    }
    else{
      event.getChannel.sendMessage("Only users with a role with administrator privileges can use this command!").queue()
      false
    }
  }






}
