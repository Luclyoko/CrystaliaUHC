package fr.luclyoko.crystaliauhc.commands;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameState;
import fr.luclyoko.crystaliauhc.gamemodes.bleach.BleachUHC;
import fr.luclyoko.crystaliauhc.map.DiamondPopulator;
import fr.luclyoko.crystaliauhc.map.GoldPopulator;
import fr.luclyoko.crystaliauhc.map.MapGenerator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MapCommand implements CommandExecutor {

    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if (args.length == 0 || Integer.parseInt(args[0]) <= 0) {
            player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "Veuillez préciser la taille à prégénérer !");
            return false;
        }

        new MapGenerator(Main.getInstance(), player.getWorld(), Integer.parseInt(args[0]), player.getLocation().getBlockX(), player.getLocation().getBlockZ());
        player.getWorld().getPopulators().add(new DiamondPopulator());
        player.getWorld().getPopulators().add(new GoldPopulator());
        player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "Démarrage de la prégénération du monde " + player.getWorld().getName() + "!");

        return true;
    }
}
