package fr.luclyoko.crystaliauhc.commands;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.guis.devguis.DevMainGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DevCommand implements CommandExecutor {

    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player)sender;

        main.getGuiManager().open(player, DevMainGui.class);

        return true;
    }
}
