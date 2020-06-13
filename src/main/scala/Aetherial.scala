import cats.effect._
import com.typesafe.config.ConfigFactory
import doobie._
import doobie.hikari.HikariTransactor
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity

import scala.concurrent.ExecutionContext

object Aetherial {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)
    val transactor: Resource[IO, HikariTransactor[IO]] = {
      for {
        ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC
        be <- Blocker[IO] // our blocking EC
        xa <- HikariTransactor.newHikariTransactor[IO](
          "org.postgresql.Driver", // driver classname
          s"jdbc:postgresql://" +
            s"${config.getString("secrets.databaseIP")}:" +
            s"${config.getString("secrets.databasePort")}/" +
            s"${config.getString("secrets.databaseName")}", // connect URL
          config.getString("secrets.databaseUser"), // username
          config.getString("secrets.databasePass"), // password
          ce, // await connection here
          be // execute JDBC operations here
        )
      } yield xa
    }

    JDABuilder.createDefault(config.getString("secrets.discordToken"))
      .addEventListeners(new AetherialListener(config.getString("secrets.botPrefix"), transactor))
      .setActivity(Activity.playing(s"Type ${config.getString("secrets.botPrefix")}help"))
      .build()
  }
}