package fr.luclyoko.crystaliauhc.discordbot;

import fr.luclyoko.crystaliauhc.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.util.EnumSet;
import java.util.logging.Level;

public class Bot {
    private Main main;
    private JDA jda;

    public JDA getJda() {
        return jda;
    }

    private Guild guild;

    public Guild getGuild() {
        return guild;
    }

    private TextChannel textChannel;

    public TextChannel getTextChannel() {
        return textChannel;
    }

    public Bot(Main main) {
        this.main = main;
    }

    public void initBot() throws InterruptedException {
        jda = JDABuilder.createDefault(Config.get("token"),
                        EnumSet.allOf(GatewayIntent.class))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.NONE)
                .setActivity(Activity.playing("UHC"))
                .addEventListeners(new DiscordListeners(main, this))
                .build()
                .awaitReady();
        if (jda.getTextChannelById(Config.get("channel_id")) == null) {
            main.getServer().getLogger().log(Level.SEVERE, "Can't find TextChannel for discord Bot, shutting the Bot down!");
            jda.shutdown();
            return;
        }
        this.textChannel = jda.getTextChannelById(Config.get("channel_id"));
        main.getServer().getPluginManager().registerEvents(new IngameListeners(main, this), main);
    }
}
