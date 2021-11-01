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
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if (args.length == 0) {
            switch (Main.getInstance().getGameManager().getGameState()) {
                case LOADING:
                    Main.getInstance().getGameManager().setGamemodeUhc(new BleachUHC(Main.getInstance().getGameManager()));
                    Main.getInstance().getGameManager().setGameState(GameState.STARTING);
                    return true;
                case STARTING:
                    Main.getInstance().getGameManager().setGameState(GameState.PLAYING);
                    return true;
                case PLAYING:
                    Main.getInstance().getGameManager().setGameState(GameState.FINISHED);
                    return true;
                case FINISHED:
                    Main.getInstance().getGameManager().setGameState(GameState.LOADING);
                    return true;
            }
        }

        if (args.length == 0 || Integer.parseInt(args[0]) <= 0) {
            player.sendMessage("Veuillez préciser la taille à prégénérer !");
            return false;
        }

        new MapGenerator(Main.getInstance(), player.getWorld(), Integer.parseInt(args[0]), player.getLocation().getBlockX(), player.getLocation().getBlockZ());
        player.getWorld().getPopulators().add(new DiamondPopulator());
        player.getWorld().getPopulators().add(new GoldPopulator());
        player.sendMessage("Démarrage de la prégénération du monde " + player.getWorld().getName() + "!");

        return true;
    }
}
