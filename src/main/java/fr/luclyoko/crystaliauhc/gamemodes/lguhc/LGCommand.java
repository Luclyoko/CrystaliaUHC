package fr.luclyoko.crystaliauhc.gamemodes.lguhc;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.*;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGRole;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.InfectPereDesLoups;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Analyste;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Pretresse;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Sorciere;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import java.util.List;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class LGCommand implements TabExecutor {
    private Main main;

    private GameManager gameManager;

    public LGCommand(Main main) {
        this.main = main;
        this.gameManager = main.getGameManager();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(this.gameManager.getGamemodeUhc() instanceof LGUHC))
            return false;
        if (!(sender instanceof Player))
            return false;
        Player player = (Player)sender;
        if (args.length > 0) {
            CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(player);
            switch (args[0]) {
                case "infect":
                    if (args.length > 1) {
                        Optional<Player> opPlayer = Optional.ofNullable(Bukkit.getPlayer(args[1]));
                        if (opPlayer.isPresent()) {
                            Player target = opPlayer.get();
                            CrystaliaPlayer crystaliaTarget = this.main.getPlayerManager().getExactPlayer(target);
                            LGRole playerRole = (LGRole)crystaliaPlayer.getRole();
                            LGRole targetRole = (LGRole)crystaliaTarget.getRole();
                            if (playerRole instanceof InfectPereDesLoups) {
                                InfectPereDesLoups ipdl = (InfectPereDesLoups)playerRole;
                                if (!ipdl.hasInfected() &&
                                        ipdl.getCanInfectPlayer().contains(crystaliaTarget) && !crystaliaTarget.isAlive())
                                    this.main.getServer().getPluginManager().callEvent((Event)new LGInfectionEvent(crystaliaTarget, crystaliaPlayer));
                            }
                        }
                    }
                    return true;
                case "rez":
                    if (args.length > 1) {
                        Optional<Player> opPlayer = Optional.ofNullable(Bukkit.getPlayer(args[1]));
                        if (opPlayer.isPresent()) {
                            Player target = opPlayer.get();
                            CrystaliaPlayer crystaliaTarget = this.main.getPlayerManager().getExactPlayer(target);
                            LGRole playerRole = (LGRole)crystaliaPlayer.getRole();
                            LGRole targetRole = (LGRole)crystaliaTarget.getRole();
                            if (playerRole instanceof Sorciere) {
                                Sorciere sorciere = (Sorciere)playerRole;
                                if (!sorciere.hasUsedRez() && sorciere.getCanRezPlayer().contains(crystaliaTarget) && !crystaliaTarget.isAlive())
                                    this.main.getServer().getPluginManager().callEvent((Event)new LGSorciereRezEvent(crystaliaTarget, crystaliaPlayer));
                            }
                        }
                    }
                    return true;
                case "espionner":
                    if (args.length > 1) {
                        Optional<Player> opPlayer = Optional.ofNullable(Bukkit.getPlayer(args[1]));
                        if (opPlayer.isPresent()) {
                            Player target = opPlayer.get();
                            CrystaliaPlayer crystaliaTarget = this.main.getPlayerManager().getExactPlayer(target);
                            LGRole playerRole = (LGRole)crystaliaPlayer.getRole();
                            LGRole targetRole = (LGRole)crystaliaTarget.getRole();
                            if (playerRole instanceof Pretresse) {
                                Pretresse pretresse = (Pretresse)playerRole;
                                if (crystaliaTarget.isAlive() && crystaliaPlayer.isAlive() && pretresse.getMaxHealth() > 4 && player.getWorld().equals(target.getWorld()) && player.getLocation().distance(target.getLocation()) <= 10.0D)
                                    this.main.getServer().getPluginManager().callEvent((Event)new LGEspionnageEvent(crystaliaTarget, crystaliaPlayer));
                            }
                        }
                    }
                    return true;
                case "analyser":
                    if (args.length > 1) {
                        Optional<Player> opPlayer = Optional.ofNullable(Bukkit.getPlayer(args[1]));
                        if (opPlayer.isPresent()) {
                            Player target = opPlayer.get();
                            CrystaliaPlayer crystaliaTarget = this.main.getPlayerManager().getExactPlayer(target);
                            LGRole playerRole = (LGRole)crystaliaPlayer.getRole();
                            LGRole targetRole = (LGRole)crystaliaTarget.getRole();
                            if (playerRole instanceof Analyste) {
                                Analyste analyste = (Analyste)playerRole;
                                if (crystaliaTarget.isAlive() && !analyste.hasUsedAnalyse() && analyste.getObservedPlayers().containsKey(crystaliaTarget) && !((List)analyste.getObservedPlayers().get(crystaliaTarget)).isEmpty())
                                    this.main.getServer().getPluginManager().callEvent((Event)new LGAnalyseEvent(crystaliaTarget, crystaliaPlayer));
                            }
                        }
                    }
                    return true;
                case "observer":
                    if (args.length > 1) {
                        Optional<Player> opPlayer = Optional.ofNullable(Bukkit.getPlayer(args[1]));
                        if (opPlayer.isPresent()) {
                            Player target = opPlayer.get();
                            CrystaliaPlayer crystaliaTarget = this.main.getPlayerManager().getExactPlayer(target);
                            LGRole playerRole = (LGRole)crystaliaPlayer.getRole();
                            LGRole targetRole = (LGRole)crystaliaTarget.getRole();
                            if (playerRole instanceof Analyste) {
                                Analyste analyste = (Analyste)playerRole;
                                if (this.main.getPlayerManager().getAliveGamePlayers().contains(crystaliaTarget) && analyste.getLeftUses() > 0)
                                    this.main.getServer().getPluginManager().callEvent((Event)new LGObserveEvent(crystaliaTarget, crystaliaPlayer));
                            }
                        }
                    }
                    return true;
                case "role":
                    if (crystaliaPlayer.isAlive() && !crystaliaPlayer.isSpec() && ((LGUHC)this.gameManager.getGamemodeUhc()).getRolesManager().hasTriggered())
                        crystaliaPlayer.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + ((LGRole)crystaliaPlayer.getRole()).getFullDescription());
                    return true;
            }
            player.sendMessage(this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cLa commande est incorrecte ou incomplète.");
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
