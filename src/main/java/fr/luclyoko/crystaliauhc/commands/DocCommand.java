package fr.luclyoko.crystaliauhc.commands;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class DocCommand implements TabExecutor {

    private final Main main;

    public DocCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player))
            return false;
        Player player = (Player) commandSender;

        StringBuilder sb = new StringBuilder("\n§b§lVoici le lien vers le document du serveur :\n \n§7» ");
        sb.append("https://luclyoko.gitbook.io/crystalia/")
                .append("\n ");

        player.sendMessage(sb.toString());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.emptyList();
    }
}
