package me.aethen.aetherial.commands.birthday
import me.aethen.aetherial.commands.SubCommand
import cats.effect.{IO, Resource}
import doobie.Transactor
import doobie.hikari.HikariTransactor
import doobie.implicits._
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent


object SetBirthdayChannel extends SubCommand {
  override def execute(event: MessageReceivedEvent, xa: Transactor[IO]): Unit = {
    if(!validateCommand(event)){
      event.getChannel.sendMessage("Administrator privileges are required to use this command. If you are an administrator, make sure there are no additional arguments after 'channel'").queue()
      return
    }

    val serverId = event.getGuild.getId
    val channelId = event.getChannel.getId

    val result =
      sql"SELECT fn_set_birthday_channel(CAST($serverId as BIGINT), CAST($channelId as BIGINT))"
        .query[Boolean]
        .unique
        .transact(xa)
    .unsafeRunSync()

    if(result){
      event.getChannel.sendMessage("The bot will post birthday announcements on this channel").queue()
    }
  }

  override def validateCommand(event: MessageReceivedEvent): Boolean = {

    if(event.getMember.hasPermission(Permission.ADMINISTRATOR)){
      val message = event.getMessage.getContentRaw.split("\\s+")
      if(message.length == 2){
        return true
      }
    }
     false
  }






}
