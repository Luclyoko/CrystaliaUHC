package fr.luclyoko.crystaliauhc.commands;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.guis.devguis.DevMainGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.HostMainGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HostCommand implements CommandExecutor {

    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player)sender;

        if (args.length > 0 && args[0].equalsIgnoreCase("config")) main.getGuiManager().open(player, HostMainGui.class);

        return true;
    }
}
