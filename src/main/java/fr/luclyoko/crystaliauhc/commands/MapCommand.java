package fr.luclyoko.crystaliauhc.commands;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.map.MapGenerator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class MapCommand implements TabExecutor {
    private final Main main;

    public MapCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player))
            return false;
        Player player = (Player)commandSender;
        if (args.length == 0 || Integer.parseInt(args[0]) <= 0) {
            player.sendMessage(this.main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§cVeuillez préciser la taille prégénérer !");
            return false;
        }
        new MapGenerator(this.main, player.getWorld(), Integer.parseInt(args[0]), player.getLocation().getBlockX(), player.getLocation().getBlockZ());
        player.sendMessage(this.main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aDémarrage de la prégénération du monde " + player.getWorld().getName() + " !");
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1)
            return Arrays.asList(new String[] { "100", "250", "500", "1000", "1500", "2000" });
        return null;
    }
}
