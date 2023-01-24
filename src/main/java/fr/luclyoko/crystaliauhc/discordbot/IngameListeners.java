package fr.luclyoko.crystaliauhc.discordbot;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class IngameListeners implements Listener {

    private final Main main;
    private final Bot bot;

    public IngameListeners(Main main, Bot bot) {
        this.main = main;
        this.bot = bot;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;

        StringBuilder sb = new StringBuilder("[MC Chat] ");
        sb.append(event.getPlayer().getDisplayName())
                .append(" : ")
                .append(event.getMessage());

        bot.getTextChannel().sendMessage(sb.toString()).queue();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) return;

        StringBuilder sb = new StringBuilder("[MC Command] ");
        sb.append(event.getPlayer().getDisplayName())
                .append(" >> ")
                .append(event.getMessage());

        bot.getTextChannel().sendMessage(sb.toString()).queue();
    }
}
