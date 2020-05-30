import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.JDABuilder
import com.typesafe.config.ConfigFactory

object Aetherial {
  def main(args: Array[String]): Unit = {
    JDABuilder.createDefault(ConfigFactory.load().getString("secrets.discordToken"))
      .addEventListeners(new AetherialListener(ConfigFactory.load().getString("secrets.botPrefix")))
      .setActivity(Activity.playing("Type (!/?/.)help"))
      .build()
  }
}