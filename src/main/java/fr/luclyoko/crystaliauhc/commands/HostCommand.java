package fr.luclyoko.crystaliauhc.commands;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.guis.hostguis.HostMainGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.enchantsguis.EnchantEditGui;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class HostCommand implements TabExecutor {
    private final Main main;

    private final GameManager gameManager;

    public HostCommand(Main main) {
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
        if (args.length > 0) {
            switch (args[0]) {
                case "config":
                    this.main.getGuiManager().open(player, HostMainGui.class);
                    return true;
                case "say":
                    if (args.length >= 2) {
                        StringBuilder message = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            message.append(args[i]);
                            if (i != args.length - 1)
                                message.append(" ");
                        }
                        message = new StringBuilder(message.toString().replaceAll("&", "§"));
                                Bukkit.broadcastMessage("\n§8§l• §c§l" + player.getName() + " §8» §r" + message + "\n ");
                    } else {
                        player.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cVous n'avez pas précisé de message.");
                    }
                    return true;
                case "name":
                    if (args.length >= 2) {
                        StringBuilder message = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            message.append(args[i]);
                            if (i != args.length - 1)
                                message.append(" ");
                        }
                        message = new StringBuilder(message.toString().replaceAll("&", "§"));
                        this.gameManager.getGamemodeUhc().setDisplayName(ChatColor.RESET + message.toString());
                    } else {
                        player.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cVous n'avez pas précisé de nom.");
                    }
                    return true;
                case "enchant":
                    if (player.getGameMode().equals(GameMode.CREATIVE) && player.getItemInHand() != null) {
                        this.main.getGuiManager().open(player, EnchantEditGui.class);
                    } else {
                        player.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cVous ne pouvez pas faire ceci pour le moment.");
                    }
                    return true;
                case "finish":
                    if (!this.gameManager.isStarted() && player.getGameMode().equals(GameMode.CREATIVE)) {
                        this.gameManager.getGameSettings().setStartInventory(player.getInventory().getContents());
                        this.gameManager.getGameSettings().setStartArmor(player.getInventory().getArmorContents());
                        player.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aInventaire de départ enregistré avec succès.");
                                player.setGameMode(GameMode.ADVENTURE);
                        player.teleport(this.main.getMapManager().getLobby().getHighestBlockAt(0, 0).getLocation().add(0.5D, 5.0D, 0.5D));
                        player.getInventory().clear();
                        player.getInventory().setArmorContents(new org.bukkit.inventory.ItemStack[4]);
                        if (this.main.getTeamManager().getTeamsSize() > 1) {
                            CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(player);
                            player.getInventory().setItem(0, this.main.getTeamManager().getTeamsBannerSelection(crystaliaPlayer));
                        }
                    }
                    return true;
            }
            player.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cLa commande est incorrecte ou incomplète.");
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1)
            return Arrays.asList(new String[] { "config", "say", "name" });
        return null;
    }
}
