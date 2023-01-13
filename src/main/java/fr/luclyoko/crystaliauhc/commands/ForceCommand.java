package fr.luclyoko.crystaliauhc.commands;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.timers.Timer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ForceCommand implements TabExecutor {
    private final Main main;

    private final GameManager gameManager;

    public ForceCommand(Main main) {
        this.main = main;
        this.gameManager = main.getGameManager();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player player = (Player)sender;
        if (this.gameManager.getGameSettings().getHost() == null || !this.gameManager.getGameSettings().getHost().equals(player.getUniqueId())) {
            player.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cVous n'avez pas l'autorisation de faire ceci.");
            return true;
        }
        if (!this.gameManager.isStarted()) {
            player.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cLa partie n'a pas encore commencé.");
            return true;
        }
        if (args.length > 0) {
            for (Timer timer : this.gameManager.getGameSettings().getAllTimers()) {
                if (args[0].equalsIgnoreCase(timer.getForceCommand())) {
                    timer.force();
                    return true;
                }
            }
            player.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cLa commande est incorrecte ou incomplète.");
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1) {
            List<String> tabComp = new ArrayList<>();
            this.gameManager.getGameSettings().getAllTimers().forEach(timer -> tabComp.add(timer.getForceCommand()));
            return tabComp;
        }
        return null;
    }
}
