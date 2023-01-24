package fr.luclyoko.crystaliauhc.discordbot;

import fr.luclyoko.crystaliauhc.Main;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class DiscordListeners extends ListenerAdapter {

    private final Main main;
    private final Bot bot;

    public DiscordListeners(Main main, Bot bot) {
        this.main = main;
        this.bot = bot;
    }

    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getChannelType().equals(ChannelType.TEXT)) return;
        if (event.getAuthor().equals(bot.getJda().getSelfUser())) return;
        if (!event.getChannel().equals(bot.getTextChannel())) return;

        User user = event.getAuthor();
        Message message = event.getMessage();

        StringBuilder sb = new StringBuilder("§7[§3Discord§7] §f");
        sb.append(user.getName())
                .append("§8: §f")
                .append(message.getContentDisplay());

        Bukkit.broadcastMessage(sb.toString());
    }
}
